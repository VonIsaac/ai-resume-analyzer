package com.example.ai_resume.core.config;

import com.example.ai_resume.domain.user.entity.TbUserEntity;
import com.example.ai_resume.domain.user.enums.Role;
import com.example.ai_resume.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            TbUserEntity demo = new TbUserEntity();
            demo.setEmail("demo@example.com");
            demo.setPasswordHash("$2a$10$placeholderHashUntilAuthIsBuilt");
            demo.setFullName("Demo User");
            demo.setRole(Role.USER);
            userRepository.save(demo);
        }
    }
}
