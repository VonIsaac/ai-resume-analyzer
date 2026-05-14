package com.example.ai_resume.domain.resume.controller;

import com.example.ai_resume.core.security.SecurityUtils;
import com.example.ai_resume.domain.resume.dto.ResumeDTO;
import com.example.ai_resume.domain.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/** Controller for managing resume uploads and text extraction. */
@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@Tag(name = "Resumes", description = "Upload and manage resume PDFs")
public class ResumeRestController {

    private final ResumeService resumeService;

    /**
     * Endpoint to upload a PDF resume and extract its text content. The extracted text is returned
     *
     * @param file - the PDF file containing the resume to be uploaded and processed
     * @return - a ResponseEntity containing the ResumeDTO with extracted text and metadata, along with HTTP status
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a PDF resume and extract its text")
    public ResponseEntity<ResumeDTO> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.uploadResume(file, SecurityUtils.currentUserId()));
    }
    /**
     * Endpoint to retrieve all resumes owned by the current user, newest first.
     *
     * @return a ResponseEntity containing a list of ResumeDTOs (empty list if none), along with HTTP status
     */
    @GetMapping
    @Operation(summary = "Get all resumes for the current user")
    public ResponseEntity<List<ResumeDTO>> getResumeList() {
        return ResponseEntity.ok(resumeService.getResumeList(SecurityUtils.currentUserId()));
    }

    /**
     * Endpoint to retrieve a resume by its unique identifier (ID).
     *
     * @param id the unique identifier of the resume to be retrieved
     * @return a ResponseEntity containing the ResumeDTO with the requested resume's information, along with HTTP status
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get resume by ID")
    public ResponseEntity<ResumeDTO> getResumeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(resumeService.getResumeById(id, SecurityUtils.currentUserId()));
    }
}