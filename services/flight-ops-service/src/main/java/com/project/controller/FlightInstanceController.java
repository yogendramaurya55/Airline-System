package com.project.controller;

import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.FlightInstanceResponse;
import com.project.service.FlightInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-instances")
public class FlightInstanceController {

    private final FlightInstanceService flightInsService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightInstanceRequest request
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(flightInsService.createFlightInstance(airlineId, request));

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightInsService.getFlightInstanceById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirlineId(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            Pageable pageable

    ) {
        return ResponseEntity.ok(flightInsService.getFlightInstanceByAirlineId(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                flightId,
                onDate,
                pageable

        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            @Valid @RequestBody FlightInstanceRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightInsService.updateFlightInstanceInstance(id , request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(@PathVariable Long id) throws Exception {
        flightInsService.deleteFlightInstance(id);

        return ResponseEntity.ok(new ApiResponse("flight instance deleted successfully"));
    }
}
