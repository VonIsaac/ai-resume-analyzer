package com.example.ai_resume.domain.job_description.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/** Implementation for custom JD queries. Empty until needed. */
@Repository
public class JobDescriptionCustomRepositoryImpl implements JobDescriptionCustomRepository {

    /**
     * The entity manager for executing custom queries.
     */
    @PersistenceContext
    private EntityManager entityManager;
}
