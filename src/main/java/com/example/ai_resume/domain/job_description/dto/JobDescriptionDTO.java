package com.example.ai_resume.domain.job_description.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/** Response DTO for JobDescription (server → frontend). */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class JobDescriptionDTO extends JobDescriptionRequestDTO {

    @Schema(description = "JD ID")
    private Long id;

    @Schema(description = "USER ID")
    private Long userId;

    @Schema(description = "JOB TITLE")
    private String jobTitle;

    @Schema(description = "COMPANY NAME")
    private String companyName;

    @Schema(description = "CREATED AT")
    private Instant createdAt;

    @Schema(description = "UPDATED AT")
    private Instant updatedAt;
}