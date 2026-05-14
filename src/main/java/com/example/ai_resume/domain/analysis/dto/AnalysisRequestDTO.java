package com.example.ai_resume.domain.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Request payload for creating an analysis (frontend → server). */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnalysisRequestDTO {

    @Schema(description = "RESUME ID to analyze")
    @NotNull(message = "{REQUIRED.FIELD}")
    private Long resumeId;

    @Schema(description = "JOB DESCRIPTION ID to analyze against")
    @NotNull(message = "{REQUIRED.FIELD}")
    private Long jobDescriptionId;
}
