package com.example.ai_resume.domain.auth.dto;

import com.example.ai_resume.domain.user.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** Data Transfer Object for authentication response containing JWT and user details. */
@Getter
@Setter
@Builder
public class AuthResponseDTO {

    @Schema(description = "JWT access token (send as 'Authorization: Bearer <token>')")
    private String token;

    @Schema(description = "The id of the authenticated user")
    private Long userId;

    @Schema(description = "The email of the authenticated user")
    private String email;

    @Schema(description = "The full name of the authenticated user")
    private String fullName;

    @Schema(description = "The role of the authenticated user (e.g. USER)")
    private Role role;
}