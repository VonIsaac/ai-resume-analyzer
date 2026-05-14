package com.example.ai_resume.domain.analysis.dto;

import com.example.ai_resume.domain.analysis.enums.AnalysisStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.Instant;

/** Response DTO for an Analysis (server → frontend). */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnalysisDTO extends AnalysisRequestDTO {

    @Schema(description = "ANALYSIS ID")
    private Long id;

    @Schema(description = "USER ID")
    private Long userId;

    @Schema(description = "STATUS: PENDING, COMPLETE, or FAILED")
    private AnalysisStatus status;

    @Schema(description = "Match score 0-100 (null until status=COMPLETE)")
    private Integer matchScore;

    @Schema(description = "Full structured LLM result (null until status=COMPLETE)")
    private AnalysisResult result;

    @Schema(description = "Error message (only set when status=FAILED)")
    private String errorMessage;

    @Schema(description = "CREATED AT")
    private Instant createdAt;

    @Schema(description = "UPDATED AT")
    private Instant updatedAt;

    @Schema(description = "COMPLETED AT (null until status leaves PENDING)")
    private Instant completedAt;
}