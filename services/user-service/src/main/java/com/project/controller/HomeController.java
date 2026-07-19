package com.project.controller;

import com.project.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<ApiResponse> status(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse("User service is online"));
    }

}
