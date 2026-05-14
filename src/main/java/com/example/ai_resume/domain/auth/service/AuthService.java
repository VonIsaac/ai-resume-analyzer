package com.example.ai_resume.domain.auth.service;

import com.example.ai_resume.domain.auth.dto.AuthResponseDTO;
import com.example.ai_resume.domain.auth.dto.LoginRequestDTO;
import com.example.ai_resume.domain.auth.dto.SignupRequestDTO;

/** Authentication operations (signup, login). */
public interface AuthService {

    /**
     * Register a new user and return a signed JWT.
     *
     * @param request signup payload (email, password, fullName)
     * @return the new user's info plus a JWT
     */
    AuthResponseDTO signup(SignupRequestDTO request);

    /**
     * Verify credentials and return a signed JWT.
     *
     * @param request login payload (email, password)
     * @return the user's info plus a JWT
     */
    AuthResponseDTO login(LoginRequestDTO request);
}