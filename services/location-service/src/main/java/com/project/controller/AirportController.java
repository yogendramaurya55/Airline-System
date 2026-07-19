package com.project.controller;

import com.project.payload.request.AirportRequest;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.ApiResponse;
import com.project.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @RequestBody @Valid AirportRequest request
            ) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.createAirport(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(
            @PathVariable Long id
    )throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.getAirportById(id));
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.getAllAirports());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportByCityId(
            @PathVariable Long cityId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.getAirportByCityId(cityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(
            @RequestBody @Valid AirportRequest request,
            @PathVariable Long id
    )throws Exception{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.updateAirport(id , request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirport(
            @PathVariable Long id
    )throws Exception{
        airportService.deleteAirport(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse("Airport deleted successfully"));
    }

}
