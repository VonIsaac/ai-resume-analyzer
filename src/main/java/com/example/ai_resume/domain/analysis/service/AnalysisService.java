package com.example.ai_resume.domain.analysis.service;

import java.util.List;
import com.example.ai_resume.domain.analysis.dto.AnalysisDTO;
import com.example.ai_resume.domain.analysis.dto.AnalysisRequestDTO;

/** Service interface for handling resume-vs-JD analyses. */
public interface AnalysisService {

    /**
     * Runs an LLM-powered analysis comparing a resume against a job description.
     *
     * @param request {resumeId, jobDescriptionId}
     * @param userId  the id of the user requesting the analysis (owner)
     * @return the completed (or failed) AnalysisDTO
     */
    AnalysisDTO createAnalysis(AnalysisRequestDTO request, Long userId);

    /**
     * Retrieves a list of analyses for the specified user, ordered by creation time descending (most recent first).
     *
     * @param userId - the id of the user whose analyses to retrieve
     * @return a list of AnalysisDTOs belonging to the user, ordered by createdAt descending
     */
    List<AnalysisDTO> getListOfAnalysis(Long userId);

    /**
     * Retrieves an analysis by its unique identifier (ID), enforcing that it belongs to the given user.
     *
     * @param id - the unique identifier of the analysis to retrieve
     * @param userId - the id of the current user (must own the analysis)
     * @return the analysis DTO
     */
    AnalysisDTO getAnalysisById(Long id, Long userId);
}