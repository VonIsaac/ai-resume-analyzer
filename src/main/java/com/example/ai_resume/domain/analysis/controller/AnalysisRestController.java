package com.example.ai_resume.domain.analysis.controller;

import com.example.ai_resume.core.security.SecurityUtils;
import com.example.ai_resume.domain.analysis.dto.AnalysisDTO;
import com.example.ai_resume.domain.analysis.dto.AnalysisRequestDTO;
import com.example.ai_resume.domain.analysis.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/** Controller for creating and reading analyses. */
@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
@Tag(name = "Analyses", description = "Resume  Job Description analysis (LLM)")
public class AnalysisRestController {

    private final AnalysisService analysisService;

    /**
     * Creates a new analysis by calling the LLM with the resume + JD text.
     * Synchronous — request blocks until the LLM call completes (5-15 sec typical).
     */
    @PostMapping
    @Operation(summary = "Run an LLM analysis comparing a resume to a job description")
    public ResponseEntity<AnalysisDTO> create(@RequestBody @Valid AnalysisRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(analysisService.createAnalysis(request, SecurityUtils.currentUserId()));
    }

    /**
     * Retrieves a list of analyses for the current user, ordered by createdAt descending (newest first).
     *
     * @return ResponseEntity with the list of AnalysisDTO
     */
    @GetMapping
    @Operation(description =  "Get all analyses for the current user, ordered by createdAt descending (newest first)")
    public ResponseEntity<List<AnalysisDTO>> getListOfAnalysis() {
        return ResponseEntity.ok(analysisService.getListOfAnalysis(SecurityUtils.currentUserId()));
    }

    /**
     * Retrieves an analysis by its unique identifier (ID) and returns an AnalysisDTO containing the analysis details.
     *
     * @param id - the unique identifier of the analysis to retrieve
     * @return - ResponseEntity with the requested AnalysisDTO and HTTP 200, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(description = "Get a specific analysis by its ID")
    public ResponseEntity<AnalysisDTO> getAnalysisById(@PathVariable Long id ) {
        return ResponseEntity.ok(analysisService.getAnalysisById(id, SecurityUtils.currentUserId()));
    }
}