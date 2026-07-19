package com.project.controller;

import com.project.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<String> home(){
        ApiResponse apiResponse = new ApiResponse();
        return ResponseEntity.ok("Hello this is airline core services");
    }
}
