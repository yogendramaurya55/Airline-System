package com.project.controller;

import com.project.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public ResponseEntity<ApiResponse> homeController(){
        String msg =  "Hello everyone i am location service of airline microservices";
        ApiResponse res = new ApiResponse(msg);
        return ResponseEntity.ok().body(res);
    }
}
