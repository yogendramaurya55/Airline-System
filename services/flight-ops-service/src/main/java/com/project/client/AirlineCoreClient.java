package com.project.client;

import com.project.payload.response.AircraftResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "airline-core-service", url = "http://localhost:5002")
public interface AirlineCoreClient {

    @GetMapping("/api/aircrafts/{id}")
    ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id);
}
