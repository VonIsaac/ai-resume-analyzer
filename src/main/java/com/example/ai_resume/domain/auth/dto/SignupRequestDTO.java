package com.example.ai_resume.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Data Transfer Object for signup request containing email, password, and full name. */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SignupRequestDTO {

    @Schema(description = "User's email address")
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @Schema(description = "Plain-text password (will be BCrypt hashed)")
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Schema(description = "Display name")
    @NotBlank
    @Size(max = 255)
    private String fullName;
}