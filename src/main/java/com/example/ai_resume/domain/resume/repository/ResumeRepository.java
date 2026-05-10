package com.example.ai_resume.domain.resume.repository;
import com.example.ai_resume.domain.resume.entity.TbResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Repository interface for managing Resume entities */
@Repository
public interface ResumeRepository extends JpaRepository<TbResumeEntity, Long> {

    /**
     * Finds all resume entities associated with a specific user ID, ordered by creation timestamp in descending order.
     * @param userId - the identifier of the user whose resumes are to be retrieved, used to filter the resumes in the database
     * @return - a list of TbResumeEntity objects that belong to the specified user, ordered from newest to oldest based on the createdAt timestamp
     */
    List<TbResumeEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}