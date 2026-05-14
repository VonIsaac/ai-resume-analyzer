package com.example.ai_resume.domain.analysis.entity;

import com.example.ai_resume.domain.analysis.dto.AnalysisDTO;
import com.example.ai_resume.domain.analysis.dto.AnalysisResult;
import com.example.ai_resume.domain.analysis.enums.AnalysisStatus;
import com.example.ai_resume.domain.job_description.entity.TbJobDescriptionEntity;
import com.example.ai_resume.domain.resume.entity.TbResumeEntity;
import com.example.ai_resume.domain.user.entity.TbUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.Instant;

/** Entity representing one analysis run (one resume × one job description). */
@Entity
@Table(name = "analyses", indexes = {
@Index(name = "idx_analyses_user_id", columnList = "user_id"), @Index(name = "idx_analyses_resume_id", columnList = "resume_id"), @Index(name = "idx_analyses_job_description_id", columnList = "job_description_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TbUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private TbResumeEntity resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_description_id", nullable = false)
    private TbJobDescriptionEntity jobDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AnalysisStatus status;

    @Column(name = "match_score")
    private Integer matchScore;

    /** Full LLM output as JSON. Hibernate 6 serializes via Jackson. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "result_payload", columnDefinition = "JSON")
    private AnalysisResult resultPayload;

    @Lob
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    /** Self-mapping: entity → response DTO. */
    public AnalysisDTO toDTO() {
        return AnalysisDTO.builder()
        .id(this.id)
        .userId(
            this.user != null  
                ? this.user.getId() 
                : null
        )
        .resumeId(
            this.resume != null 
                ? this.resume.getId() 
                : null
        )
        .jobDescriptionId(
             this.jobDescription != null 
                ? this.jobDescription.getId() 
                : null
        )
        .status(this.status)
        .matchScore(this.matchScore)
        .result(this.resultPayload)
        .errorMessage(this.errorMessage)
        .createdAt(this.createdAt)
        .updatedAt(this.updatedAt)
        .completedAt(this.completedAt)
        .build();
    }
}