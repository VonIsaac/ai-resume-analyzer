package com.example.ai_resume.domain.analysis.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/** Implementation for custom analysis queries. Empty until needed. */
@Repository
public class AnalysisCustomRepositoryImpl implements AnalysisCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
}
