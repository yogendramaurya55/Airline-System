package com.project.controller;

import com.project.enums.FlightStatus;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.FlightResponse;
import com.project.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid @RequestBody FlightRequest flightRequest,
            @RequestHeader("X-Airline-Id") Long airlineId
            ) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(airlineId , flightRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightByAirline(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable
    ){
        return ResponseEntity.ok(flightService.getFlightByAirline(airlineId , departureAirportId , arrivalAirportId , pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable(value = "id") Long flightId,
            @RequestBody FlightRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(flightId , request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable(value = "id") Long airlineId,
            @RequestParam FlightStatus status
            ) throws Exception {
        return ResponseEntity.ok(flightService.changeStatus(airlineId , status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlight(
            @PathVariable(value = "id") Long flightId,
            @RequestParam("X-Airline-Id") Long airlineId
    ) throws Exception {
        flightService.deleteFlight(flightId , airlineId);
        return ResponseEntity.ok(new ApiResponse("flight deleted successfully"));
    }
}
