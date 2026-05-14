package com.example.ai_resume.domain.auth.service;

import com.example.ai_resume.domain.auth.dto.AuthResponseDTO;
import com.example.ai_resume.domain.auth.dto.LoginRequestDTO;
import com.example.ai_resume.domain.auth.dto.SignupRequestDTO;
import com.example.ai_resume.domain.auth.jwt.JwtService;
import com.example.ai_resume.domain.user.entity.TbUserEntity;
import com.example.ai_resume.domain.user.enums.Role;
import com.example.ai_resume.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** Service implementation for handling authentication operations such as signup and login. */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Registers a new user with the provided signup information and returns an AuthResponseDTO containing a JWT access token and user details.
     *
     * @param request - the signup request containing the user's registration information, such as email and password
     * @return - the new user's info plus a JWT
     */
    @Override
    @Transactional
    public AuthResponseDTO signup(SignupRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        TbUserEntity user = new TbUserEntity();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(Role.USER);

        TbUserEntity saved = userRepository.save(user);
        return buildResponse(saved);
    }

    /**
     * Authenticates a user with the provided login credentials and returns an AuthResponseDTO containing a JWT access token and user details if the credentials are valid.
     *
     * @param request - the login request containing the user's email and password for authentication
     * @return - the user's info plus a JWT if authentication is successful
     */
    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO request) {
        TbUserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return buildResponse(user);
    }

    /**
     * Builds an AuthResponseDTO containing a JWT token and user details for the given user entity.
     *
     * @param user - the user entity for which to build the response
     * @return
     */
    private AuthResponseDTO buildResponse(TbUserEntity user) {
        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        return AuthResponseDTO.builder()
            .token(token)
            .userId(user.getId())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .role(user.getRole())
            .build();
    }
}