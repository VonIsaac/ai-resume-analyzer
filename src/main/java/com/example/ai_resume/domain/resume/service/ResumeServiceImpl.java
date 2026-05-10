package com.example.ai_resume.domain.resume.service;

import com.example.ai_resume.domain.resume.dto.ResumeDTO;
import com.example.ai_resume.domain.resume.entity.TbResumeEntity;
import com.example.ai_resume.domain.resume.repository.ResumeRepository;
import com.example.ai_resume.domain.user.entity.TbUserEntity;
import com.example.ai_resume.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

/** * Implementation of the ResumeService interface for handling resume-related operations.*/
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    
    /**
     * Uploads a PDF resume, extracts its text content, and returns a ResumeDTO containing the extracted text and metadata.
     *
     * @param file the PDF file containing the resume to be uploaded and processed, expected to be a valid PDF document
     * @param userId the ID of the user whose resume to retrieve, used to query the database for the specific resume entity
     * @return the resume DTO.
     */
    @Override
    @Transactional
    public ResumeDTO uploadResume(MultipartFile file, Long userId) {
        validateFile(file);

        TbUserEntity owner = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "User not found: " + userId
        ));

        TbResumeEntity entity = TbResumeEntity.builder()
                .user(owner)
                .fileName(file.getOriginalFilename())
                .extractedText(extractTextFromPdf(file))
                .build();

        TbResumeEntity saved = resumeRepository.save(entity);
        return saved.toDTO();
    }

    /**
     * Returns all resumes owned by the given user, newest first.
     *
     * @param userId the owner's user id
     * @return list of ResumeDTOs (empty list if user has no resumes)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResumeDTO> getResumeList(Long userId) {
        return resumeRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(TbResumeEntity::toDTO)
                .toList();
    }
    /**
     * Returns a ResumeDTO containing the extracted text and metadata of the requested resume.
     * Throws a 404 ResponseStatusException when the resume is not found.
     *
     * @param id the unique identifier of the resume to be retrieved
     * @return the resume DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ResumeDTO getResumeById(Long id) {
        return resumeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found: " + id)).toDTO();
    }

    /**
     * Validates the uploaded file to ensure it is not empty and has a .pdf extension.
     * @param file - the file to validate, expected to be a MultipartFile representing a PDF document
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Only PDF files are supported for now");
        }
    }

    /**
     * Extracts text content from the uploaded PDF file using Apache PDFBox.
     * @param file - the PDF file from which to extract text, expected to be a valid PDF document
     * @return - a String containing the extracted text from the PDF, or an error response if the PDF cannot be processed
     */
    private String extractTextFromPdf(MultipartFile file) {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            return new PDFTextStripper().getText(document);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Failed to read PDF: " + e.getMessage(), e);
        }
    }
}