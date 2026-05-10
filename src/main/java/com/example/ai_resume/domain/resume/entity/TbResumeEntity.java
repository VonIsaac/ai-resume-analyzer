package com.example.ai_resume.domain.resume.entity;

import com.example.ai_resume.domain.resume.dto.ResumeDTO;
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

/** Entity representing a resume in the database */
@Entity
@Table(name = "resumes", indexes = @Index(name = "idx_resumes_user_id", columnList = "user_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private TbUserEntity user;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Lob
    @Column(name = "extracted_text", columnDefinition = "LONGTEXT", nullable = false)
    private String extractedText;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Converts this entity into a ResumeDTO for returning to the frontend.
     *
     * @return the corresponding ResumeDTO
     */
    public ResumeDTO toDTO() {
        return ResumeDTO.builder()
                .id(this.id)
                .userId(this.user != null ? this.user.getId() : null)
                .fileName(this.fileName)
                .extractedText(this.extractedText)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
