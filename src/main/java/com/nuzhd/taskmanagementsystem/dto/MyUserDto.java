package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User info returned on successful user registration")
public record MyUserDto(
        @Schema(description = "User login", example = "test_login123", implementation = String.class)
        String username,
        @Schema(description = "User email", example = "example@gmail.com", implementation = String.class)
        String email
) {
}
