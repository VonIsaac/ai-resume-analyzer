package com.example.ai_resume.domain.user.service;

import com.example.ai_resume.domain.user.dto.UserDTO;

/** Service interface for user-related operations. */
public interface UserService {

    /**
     * Retrieves the currently authenticated user.
     *
     * @param userId the id of the user (taken from the JWT)
     * @return the user as a UserDTO
     */
    UserDTO getCurrentUser(Long userId);
}
