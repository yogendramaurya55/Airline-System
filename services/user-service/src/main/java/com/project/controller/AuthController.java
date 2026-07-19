package com.project.controller;

import com.project.payload.dto.UserDTO;
import com.project.payload.request.LoginRequest;
import com.project.payload.response.AuthResponse;
import com.project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid UserDTO user
            )throws Exception{
        AuthResponse res = authService.signup(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest req
            )throws Exception{
        AuthResponse res = authService.login(req.getEmail() , req.getPassword());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }


}
