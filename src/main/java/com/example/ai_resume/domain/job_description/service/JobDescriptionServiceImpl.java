package com.example.ai_resume.domain.job_description.service;

import com.example.ai_resume.domain.job_description.dto.JobDescriptionDTO;
import com.example.ai_resume.domain.job_description.dto.JobDescriptionRequestDTO;
import com.example.ai_resume.domain.job_description.entity.TbJobDescriptionEntity;
import com.example.ai_resume.domain.job_description.repository.JobDescriptionRepository;
import com.example.ai_resume.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** Service implementation for handling job description-related operations. */
@Service
@RequiredArgsConstructor
public class JobDescriptionServiceImpl implements JobDescriptionService {

    private final JobDescriptionRepository jobDescriptionRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new job description from the request payload.
     *
     * @param request the request containing the raw JD text
     * @param userId  the id of the user creating the JD
     * @return the saved JD as a JobDescriptionDTO
     */
    @Override
    @Transactional
    public JobDescriptionDTO createJobDescription(
            JobDescriptionRequestDTO request,
            Long userId
        ) {
        return jobDescriptionRepository.save(
            TbJobDescriptionEntity.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId)))
                .jobTitle(request.getJobTitle())
                .companyName(request.getCompanyName())
                .rawText(request.getRawText())
                .build()
        ).toDTO();
    }

    /**
     * Retrieves a list of job descriptions for the specified user.
     *
     * @param userId - the id of the user whose job descriptions to retrieve
     * @return - a list of JobDescriptionDTOs belonging to the user
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobDescriptionDTO> getJobDescList(Long userId) {
        return jobDescriptionRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
        .stream()
        .map(TbJobDescriptionEntity::toDTO)
        .toList();
    }

    /**
     * Returns a JobDescriptionDTO containing the raw text and metadata of the requested job description.
     *
     * @param id the unique identifier of the job description to be retrieved
     * @return the job description DTO
     */
    @Override
    @Transactional(readOnly = true)
    public JobDescriptionDTO getJobDescById(Long id) {
        return jobDescriptionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job description not found: " + id)).toDTO();
    }
}