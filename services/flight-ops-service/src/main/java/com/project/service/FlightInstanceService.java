package com.project.service;

import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception;
    FlightInstanceResponse updateFlightInstanceInstance(Long id , FlightInstanceRequest request) throws Exception;

    FlightInstanceResponse getFlightInstanceById(Long id) throws Exception;
    Page<FlightInstanceResponse> getFlightInstanceByAirlineId(Long airlineId  , Long departureAirportId , Long arrivalAirportId , Long flightId , LocalDate onDate , Pageable pageable);
    void deleteFlightInstance(Long id) throws Exception;

}
