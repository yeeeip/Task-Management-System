package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dto for transferring user data during sign up process")
public record UserRegistrationRequest(
        @Schema(description = "User email", implementation = String.class, example = "mysimplemail@gmail.com")
        @Email(message = "Invalid email")
        String email,
        @Schema(description = "User login", implementation = String.class, example = "username_123")
        @NotBlank(message = "Username must not be blank or null")
        String username,

        @Schema(description = "User password", implementation = String.class, example = "my_pass123")
        @Size(min = 8, max = 128, message = "Password must contain at least 8 symbols")
        String password) {
}
