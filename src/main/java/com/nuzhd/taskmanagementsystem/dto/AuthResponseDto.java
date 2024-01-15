package com.nuzhd.taskmanagementsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Auth token returned on user login")
public record AuthResponseDto(
        @Schema(description = "The string representation of the token",
                example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9",
                implementation = String.class)
        String token

        ) {
}
