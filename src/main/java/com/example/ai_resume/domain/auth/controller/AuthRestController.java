package com.example.ai_resume.domain.auth.controller;

import com.example.ai_resume.domain.auth.dto.AuthResponseDTO;
import com.example.ai_resume.domain.auth.dto.LoginRequestDTO;
import com.example.ai_resume.domain.auth.dto.SignupRequestDTO;
import com.example.ai_resume.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** Public endpoints for signing up and logging in. */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Signup and login (JWT)")
public class AuthRestController {

    private final AuthService authService;

    /**
     * Registers a new user with the provided signup information and returns an AuthResponseDTO containing a JWT access token and user details.
     *
     * @param request - the signup request containing the user's registration information, such as email and password
     * @return - ResponseEntity with the AuthResponseDTO containing the JWT token and user details, and HTTP status 201 Created
     */
    @PostMapping("/signup")
    @Operation(summary = "Register a new user and receive a JWT")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody @Valid SignupRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    /**
     * Authenticates a user with the provided login credentials and returns an AuthResponseDTO containing a JWT access token and user details if the credentials are valid.
     *
     * @param request - the login request containing the user's email and password for authentication
     * @return - ResponseEntity with the AuthResponseDTO containing the JWT token and user details if authentication is successful, or HTTP status 401 Unauthorized if authentication fails
     */
    @PostMapping("/login")
    @Operation(summary = "Log in with email + password and receive a JWT")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Logs out the user. Since JWT is stateless, this endpoint does not invalidate the token on the server side. Instead, it serves as a signal to the client to delete the stored token. Always returns HTTP 200 OK with a message.
     * @return
     */
    @PostMapping("/logout")
    @Operation( summary = "Log out (client-side action)", description = "JWT is stateless, so the server has no session to destroy")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out. Discard the token on the client."));
    }
}