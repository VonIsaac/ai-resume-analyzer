package com.example.ai_resume.domain.user.dto;

import com.example.ai_resume.domain.user.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/** Public view of a user (never includes the password hash). */
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
}
