package com.project.controller;

import com.project.client.AirlineCoreClient;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final AirlineCoreClient airlineCoreServices;

    @GetMapping
    public ResponseEntity<ApiResponse> getStaus(){
        return ResponseEntity.ok(new ApiResponse("Hi I am flight operations service"));
    }

    @GetMapping("/get/aircraft/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id){

        return airlineCoreServices.getAircraftById(id);

    }
}
