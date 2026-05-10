package com.example.ai_resume.domain.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/** Data Transfer Object for Resume entity */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDTO {
    // Unique identifier for the resume
    @Size(max = 500, message = "{FULL.SIZE.EXCEPTION}")
    @Schema(description = "COMPANY ID")
    private Long id;

    // Identifier for the user who uploaded the resume
    @Size(max = 500, message = "{FULL.SIZE.EXCEPTION}")
    @Schema(description = "USER ID")
    private Long userId;

    // Original filename of the uploaded resume PDF
    @Size(max = 500, message = "{FULL.SIZE.EXCEPTION}")
    @Schema(description = "FILE NAME")
    private String fileName;
    // Extracted text content from the resume PDF
    @Size(max = 10000, message = "{FULL.SIZE.EXCEPTION}")
    @Schema(description = "EXTRACTED TEXT")
    private String extractedText;

    // Timestamp when the resume was created
    @Schema(description = "CREATED AT")
    private Instant createdAt;

    // Timestamp when the resume was last updated
    @Schema(description = "UPDATED AT")
    private Instant updatedAt;
}
