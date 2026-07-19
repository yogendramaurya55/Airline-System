package com.project.controller;

import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.ApiResponse;
import com.project.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId
            ) throws Exception {
        AircraftResponse aircraft  = aircraftService.createAircraft(request , userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAllAircraft(
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAircraftByOwnerId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long ownerId
            ) throws Exception {

        return ResponseEntity.ok(aircraftService.updateAircraft(id , request , ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAircraft(@PathVariable Long id , @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        aircraftService.deleteAircraft(id , ownerId);
        return ResponseEntity.ok(new ApiResponse("aircraft deleted successfully"));
    }
}
