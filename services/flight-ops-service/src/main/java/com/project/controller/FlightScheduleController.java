package com.project.controller;

import com.project.payload.request.FlightScheduleRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.FlightScheduleResponse;
import com.project.service.FlightScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-schedules")
public class FlightScheduleController {

    private final FlightScheduleService fsService;

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @Valid @RequestBody FlightScheduleRequest request,
            @RequestHeader("X-User-Id") Long userId
            ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fsService.crateFlightSchedule(userId , request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                fsService.getFlightScheduleById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @Valid @RequestBody FlightScheduleRequest request) throws Exception {

        return ResponseEntity.ok(fsService.updateFlightSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(@PathVariable Long id) throws Exception {

        fsService.deleteFlightSchedule(id);

        return ResponseEntity.ok(new ApiResponse("schedule removed successfully"));
    }


}
