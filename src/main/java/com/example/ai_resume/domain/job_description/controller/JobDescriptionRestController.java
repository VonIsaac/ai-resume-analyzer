package com.example.ai_resume.domain.job_description.controller;

import com.example.ai_resume.core.security.SecurityUtils;
import com.example.ai_resume.domain.job_description.dto.JobDescriptionDTO;
import com.example.ai_resume.domain.job_description.dto.JobDescriptionRequestDTO;
import com.example.ai_resume.domain.job_description.service.JobDescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for creating and managing job descriptions. */
@RestController
@RequestMapping("/api/job-descriptions")
@RequiredArgsConstructor
@Tag(name = "Job Descriptions", description = "Create and manage job descriptions")
public class JobDescriptionRestController {

    private final JobDescriptionService jobDescriptionService;

    /**
     * Endpoint to create a new job description from raw text input.
     *
     * @param request the JSON body containing rawText
     * @return ResponseEntity with the created JobDescriptionDTO and HTTP 201
     */
    @PostMapping
    @Operation(summary = "Create a new job description")
    public ResponseEntity<JobDescriptionDTO> create(@RequestBody @Valid JobDescriptionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobDescriptionService.createJobDescription(request, SecurityUtils.currentUserId()));
    }

    /**
     * Endpoint to retrieve all job descriptions owned by the current user, newest first.
     *
     * @return ResponseEntity with the list of JobDescription
     */
    @GetMapping
    @Operation(summary = "Get all job descriptions for the current user")
    public ResponseEntity<List<JobDescriptionDTO>> getJobDescList() {
        return ResponseEntity.ok(jobDescriptionService.getJobDescList(SecurityUtils.currentUserId()));
    }

    /**
     * Endpoint to retrieve a job description by its unique identifier (ID).
     *
     * @param id the unique identifier of the job description to be retrieved
     * @return ResponseEntity with the requested JobDescriptionDTO and HTTP 200, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a job description by ID")
    public ResponseEntity<JobDescriptionDTO> getJobDescById(@PathVariable Long id) {
        return ResponseEntity.ok(jobDescriptionService.getJobDescById(id, SecurityUtils.currentUserId()));
    }

}