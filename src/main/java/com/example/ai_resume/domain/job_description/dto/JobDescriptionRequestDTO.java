package com.example.ai_resume.domain.job_description.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Request payload for creating a job description */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class JobDescriptionRequestDTO {

    @Schema(description = "JOB TITLE")
    @Size(max = 255, message = "{FULL.SIZE.EXCEPTION}")
    private String jobTitle;

    @Schema(description = "COMPANY NAME")
    @Size(max = 255, message = "{FULL.SIZE.EXCEPTION}")
    private String companyName;

    @Schema(description = "RAW JD TEXT")
    @NotBlank(message = "{REQUIRED.FIELD}")
    @Size(max = 50000, message = "{FULL.SIZE.EXCEPTION}")
    private String rawText;
}