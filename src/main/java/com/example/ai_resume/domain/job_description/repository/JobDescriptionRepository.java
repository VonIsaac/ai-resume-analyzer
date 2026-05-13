package com.example.ai_resume.domain.job_description.repository;

import com.example.ai_resume.domain.job_description.entity.TbJobDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Repository interface for managing Job Description entities. */
@Repository
public interface JobDescriptionRepository extends JpaRepository<TbJobDescriptionEntity, Long>, JobDescriptionCustomRepository {

    /**
     * Finds all job descriptions for a given user, ordered by creation date descending.
     * @param userId - the id of the user whose job descriptions to retrieve
     * @return - a list of job descriptions belonging to the user
     */
    List<TbJobDescriptionEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
