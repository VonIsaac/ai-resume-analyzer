package com.example.ai_resume.domain.analysis.prompt;

/** Static prompt templates for the analysis LLM calls. */
public final class AnalysisPrompt {

    private AnalysisPrompt() {}

    /** System-message template providing overall instructions to the LLM. */
    public static final String SYSTEM = """
            You are an expert career coach and technical recruiter.
            You evaluate how well a candidate's resume matches a target job description.
            Be specific, honest, and actionable. Never invent skills the resume does not contain.
            """;

    /** User-message template with placeholders for the resume and job description text. */
    public static final String USER = """
            Compare the candidate's resume to the job description and return your analysis.

            === RESUME ===
            {resumeText}

            === JOB DESCRIPTION ===
            {jobDescriptionText}

            Scoring guidance:
            - 0-39: weak match, missing many requirements
            - 40-69: moderate match, has some requirements
            - 70-89: strong match, fits most requirements
            - 90-100: excellent match

            For matchedSkills: only include skills that appear in both texts.
            For missingSkills: only include skills explicitly required by the JD but absent from the resume.
            For suggestions: provide 3 to 6 concrete, specific improvements. Reference real sentences from the resume when possible.
            """;
}