package com.project.controller;

import com.project.payload.dto.UserDTO;
import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            /*
            -> this header is not coming from frontend
            -> after the jwt verification by middleware
            -> middleware layer adds this token to request
             */
            @RequestHeader("X-User-Email") String email
    ) throws Exception {
        UserDTO res = userService.getUserByEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id
    )throws Exception{
        UserDTO res = userService.getUserById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }
}
