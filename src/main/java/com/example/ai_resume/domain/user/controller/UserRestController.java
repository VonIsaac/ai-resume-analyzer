package com.example.ai_resume.domain.user.controller;

import com.example.ai_resume.core.security.SecurityUtils;
import com.example.ai_resume.domain.user.dto.UserDTO;
import com.example.ai_resume.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for the currently authenticated user. */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Information about the currently authenticated user")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get the currently authenticated user")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser(SecurityUtils.currentUserId()));
    }
}
