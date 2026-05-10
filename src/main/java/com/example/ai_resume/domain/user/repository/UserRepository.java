package com.example.ai_resume.domain.user.repository;

import com.example.ai_resume.domain.user.entity.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TbUserEntity, Long> {

    Optional<TbUserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
