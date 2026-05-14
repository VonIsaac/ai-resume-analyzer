package com.example.ai_resume.domain.analysis.service;

import com.example.ai_resume.domain.analysis.dto.AnalysisDTO;
import com.example.ai_resume.domain.analysis.dto.AnalysisRequestDTO;
import com.example.ai_resume.domain.analysis.dto.AnalysisResult;
import com.example.ai_resume.domain.analysis.entity.TbAnalysisEntity;
import com.example.ai_resume.domain.analysis.enums.AnalysisStatus;
import com.example.ai_resume.domain.analysis.prompt.AnalysisPrompt;
import com.example.ai_resume.domain.analysis.repository.AnalysisRepository;
import com.example.ai_resume.domain.job_description.entity.TbJobDescriptionEntity;
import com.example.ai_resume.domain.job_description.repository.JobDescriptionRepository;
import com.example.ai_resume.domain.resume.entity.TbResumeEntity;
import com.example.ai_resume.domain.resume.repository.ResumeRepository;
import com.example.ai_resume.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.time.Instant;

/* Service implementation for handling resume-vs-JD analyses. */
@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final ResumeRepository resumeRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final UserRepository userRepository;
    private final ChatClient chatClient;

    /* Constructor-based dependency injection for repositories and ChatClient. */
    public AnalysisServiceImpl(
        AnalysisRepository analysisRepository,
        ResumeRepository resumeRepository,
        JobDescriptionRepository jobDescriptionRepository,
        UserRepository userRepository,
        ChatClient.Builder chatClientBuilder
    ) {
        this.analysisRepository = analysisRepository;
        this.resumeRepository = resumeRepository;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.userRepository = userRepository;
        this.chatClient = chatClientBuilder
                .defaultSystem(AnalysisPrompt.SYSTEM)
                .build();
    }

    /**
     * Creates a new analysis request and initiates the analysis process.
     *
     * @param request - the analysis request containing resume and job description IDs
     * @param userId - the ID of the user making the request
     * @return - the DTO representing the created analysis
     */
    @Override
    @Transactional
    public AnalysisDTO createAnalysis(AnalysisRequestDTO request, Long userId) {
        TbResumeEntity resume = resumeRepository.findById(request.getResumeId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found: " + request.getResumeId()));
        TbJobDescriptionEntity jd = jobDescriptionRepository.findById(request.getJobDescriptionId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job description not found: " + request.getJobDescriptionId()));

        if (!resume.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Resume does not belong to user");
        }
        if (!jd.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Job description does not belong to user");
        }

        TbAnalysisEntity analysis = TbAnalysisEntity.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId)))
                .resume(resume)
                .jobDescription(jd)
                .status(AnalysisStatus.PENDING)
                .build();

        analysis = analysisRepository.save(analysis);

        try {
            AnalysisResult result = chatClient.prompt()
                    .user(u -> u.text(AnalysisPrompt.USER)
                            .param("resumeText", resume.getExtractedText())
                            .param("jobDescriptionText", jd.getRawText()))
                    .call()
                    .entity(AnalysisResult.class);

            analysis.setStatus(AnalysisStatus.COMPLETE);
            analysis.setMatchScore(result.matchScore());
            analysis.setResultPayload(result);
            analysis.setCompletedAt(Instant.now());
            return analysisRepository.save(analysis).toDTO();

        } catch (Exception e) {
            log.error("Analysis {} failed during LLM call", analysis.getId(), e);
            analysis.setStatus(AnalysisStatus.FAILED);
            analysis.setErrorMessage(truncate(e.getMessage(), 1000));
            analysis.setCompletedAt(Instant.now());
            return analysisRepository.save(analysis).toDTO();
        }
    }

    /**
     * Retrieves a list of analyses for the specified user, ordered by creation time descending (most recent first).
     *
     * @param userId - the id of the user whose analyses to retrieve
     * @return a list of AnalysisDTOs belonging to the user, ordered by createdAt descending
     */
    @Override
    @Transactional(readOnly = true)
    public List<AnalysisDTO> getListOfAnalysis(Long userId) {
        return analysisRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
            .stream()
            .map(TbAnalysisEntity::toDTO)
            .toList();
    }

    /**
     * Retrieves an analysis by its unique identifier (ID), but only if it belongs to the caller.
     * Throws 404 if not found and 403 if owned by another user.
     *
     * @param id - the unique identifier of the analysis to retrieve
     * @param userId - the id of the current user (must own the analysis)
     * @return the analysis DTO
     */
    @Override
    @Transactional(readOnly = true)
    public AnalysisDTO getAnalysisById(Long id, Long userId) {
        TbAnalysisEntity analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Analysis not found: " + id));
        if (!analysis.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Analysis does not belong to user");
        }
        return analysis.toDTO();
    }

    /**
     * Truncate a string to a maximum length, handling nulls safely.
     *
     * @param s - the string to truncate
     * @param max - the maximum length of the string after truncation
     * @return - the original string if it's shorter than max, otherwise a truncated version of the string; returns null if input is null
     */
    private String truncate(String s, int max) {
        if (s == null) {
            return null;
        }
        return s.length() <= max ? s : s.substring(0, max);
    }
}