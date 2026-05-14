package com.example.ai_resume.domain.analysis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** Structured output produced by the LLM. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AnalysisResult(
        @Schema(description = "Match score from 0 to 100")
        int matchScore,

        @Schema(description = "Short 2-3 sentence overview of fit")
        String summary,

        @Schema(description = "Skills present in BOTH the resume and the job description")
        List<String> matchedSkills,

        @Schema(description = "Skills required by the job description but missing from the resume")
        List<String> missingSkills,

        @Schema(description = "Specific suggestions to improve the resume for this role")
        List<Suggestion> suggestions
) {
    public record Suggestion(
            @Schema(description = "Resume section the suggestion targets, e.g. 'Experience' or 'Skills'")
            String section,

            @Schema(description = "Actionable advice")
            String advice
    ) {}
}