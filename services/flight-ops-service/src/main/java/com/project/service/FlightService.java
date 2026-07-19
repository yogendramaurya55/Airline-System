package com.project.service;

import com.project.enums.FlightStatus;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    FlightResponse createFlight(Long airlineId , FlightRequest flightRequest) throws Exception;
    Page<FlightResponse> getFlightByAirline(Long airlineId , Long departureAirportId , Long arrivalAirportId , Pageable pageable);
    FlightResponse getFlightById(Long id) throws Exception;
    FlightResponse updateFlight(Long flightId , FlightRequest request) throws Exception;
    void deleteFlight(Long airlineId ,Long id) throws Exception;
    FlightResponse changeStatus(Long id , FlightStatus status) throws Exception;
}
