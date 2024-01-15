package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dto for transferring user data during sign in process")
public record UserLoginRequest(
        @Schema(description = "User login", implementation = String.class, example = "test_user")
        @NotBlank(message = "Username must not be blank or null")
        String username,

        @Schema(description = "User password", implementation = String.class, example = "my_Pass_1234")
        @NotBlank(message = "Password must not be blank or null")
        @Size(min = 8, max = 128, message = "Password length must be from 8 to 128 symbols")
        String password) {
}
