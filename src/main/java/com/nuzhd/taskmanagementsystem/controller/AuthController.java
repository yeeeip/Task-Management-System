package com.nuzhd.taskmanagementsystem.controller;

import com.nuzhd.taskmanagementsystem.dto.AuthResponseDto;
import com.nuzhd.taskmanagementsystem.dto.MyUserDto;
import com.nuzhd.taskmanagementsystem.dto.UserLoginRequest;
import com.nuzhd.taskmanagementsystem.dto.UserRegistrationRequest;
import com.nuzhd.taskmanagementsystem.mapper.MyUserMapper;
import com.nuzhd.taskmanagementsystem.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication/authorization API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final MyUserMapper userMapper;

    public AuthController(AuthenticationService authService, MyUserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Allows users to login into the service",
            description = "Returns JWT token in case of successful login"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid password or/and email",
                    content = @Content
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody
                                                 UserLoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(
            summary = "Allows users to create new account",
            description = "Returns user login and email in case of success"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New user data",
                    content = @Content(
                            schema = @Schema(implementation = MyUserDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Validation errors",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Username is taken",
                    content = @Content
            )
    })
    @PostMapping("/register")
    public ResponseEntity<MyUserDto> registerUser(@Valid @RequestBody
                                                  UserRegistrationRequest registrationRequest) {
        return ResponseEntity.ok(
                userMapper.toDto(authService.register(registrationRequest))
        );
    }


}
