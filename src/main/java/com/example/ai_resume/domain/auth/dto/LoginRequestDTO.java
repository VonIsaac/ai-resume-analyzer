package com.example.ai_resume.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Data Transfer Object for login request containing email and password. */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LoginRequestDTO {

    @Schema(description = "User's email address")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Plain-text password")
    @NotBlank
    private String password;
}