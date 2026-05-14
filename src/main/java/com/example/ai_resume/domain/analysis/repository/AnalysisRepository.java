package com.example.ai_resume.domain.analysis.repository;

import com.example.ai_resume.domain.analysis.entity.TbAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository for managing Analysis entities. */
public interface AnalysisRepository extends JpaRepository<TbAnalysisEntity, Long>, AnalysisCustomRepository {

    /**
     * Find all analyses for a given user, ordered by creation time descending (most recent first).
     *
     * @param userId - the ID of the user whose analyses to retrieve
     * @return a list of TbAnalysisEntity objects belonging to the user, ordered by createdAt descending
     */
    List<TbAnalysisEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}