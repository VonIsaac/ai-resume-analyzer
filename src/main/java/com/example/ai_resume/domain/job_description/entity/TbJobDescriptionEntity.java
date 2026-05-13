package com.example.ai_resume.domain.job_description.entity;

import com.example.ai_resume.domain.job_description.dto.JobDescriptionDTO;
import com.example.ai_resume.domain.user.entity.TbUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/** Entity representing a job description in the database. */
@Entity
@Table(name = "job_descriptions", indexes = @Index(name = "idx_job_descriptions_user_id", columnList = "user_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbJobDescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TbUserEntity user;

    @Column(name = "job_title", length = 255)
    private String jobTitle;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Lob
    @Column(name = "raw_text", columnDefinition = "LONGTEXT", nullable = false)
    private String rawText;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /** Self-mapping: entity → response DTO. */
    public JobDescriptionDTO toDTO() {
        return JobDescriptionDTO.builder()
                .id(this.id)
                .userId(this.user != null ? this.user.getId() : null)
                .jobTitle(this.jobTitle)
                .companyName(this.companyName)
                .rawText(this.rawText)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}