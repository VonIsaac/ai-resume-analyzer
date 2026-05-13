package com.example.ai_resume.domain.job_description.service;

import java.util.List;

import com.example.ai_resume.domain.job_description.dto.JobDescriptionDTO;
import com.example.ai_resume.domain.job_description.dto.JobDescriptionRequestDTO;

/** Service interface for handling job description-related operations. */
public interface JobDescriptionService {

    /**
     * Creates a new job description from the request payload.
     *
     * @param request the request containing the raw JD text
     * @param userId  the id of the user creating the JD
     * @return the saved JD as a JobDescriptionDTO
     */
    JobDescriptionDTO createJobDescription(JobDescriptionRequestDTO request, Long userId);

    /**
     * Retrieves a list of job descriptions for the specified user.
     *
     * @param userId - the id of the user whose job descriptions to retrieve
     * @return - a list of JobDescriptionDTOs belonging to the user
     */
    List<JobDescriptionDTO> getJobDescList(Long userId);

    /**
     * Retrieves a job description by its unique identifier (ID) and returns a JobDescriptionDTO containing the JD's raw text and metadata.
     *
     * @param id the unique identifier of the job description to retrieve
     * @return the job description DTO
     */
    JobDescriptionDTO getJobDescById(Long id);
}
