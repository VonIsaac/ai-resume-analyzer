package com.example.ai_resume.domain.resume.service;

import com.example.ai_resume.domain.resume.dto.ResumeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/** Service interface for handling resume-related operations, such as uploading and processing resumes. */
public interface ResumeService {

    /**
     * Uploads a PDF resume, extracts its text content, and returns a ResumeDTO containing the extracted text and metadata.
     * @param userId - the identifier of the user uploading the resume, used to associate the resume with the correct user in the database
     * @return - a ResumeDTO containing the extracted text from the resume and relevant metadata such as file name and timestamps
     */
    ResumeDTO uploadResume(MultipartFile file, Long userId);

    /**
     * Retrieves a resume by its unique identifier (ID), enforcing that it belongs to the given user.
     * @param id - the unique identifier of the resume to be retrieved
     * @param userId - the id of the current user (must own the resume)
     * @return - a ResumeDTO containing the extracted text and metadata of the requested resume
     */
    ResumeDTO getResumeById(Long id, Long userId);

    /**
     * Retrieves all resumes owned by the given user, ordered by creation date (newest first).
     * @param userId - the identifier of the user whose resumes to retrieve
     * @return - a List of ResumeDTOs (empty list if the user has no resumes)
     */
    List<ResumeDTO> getResumeList(Long userId);
}
