package com.project.service;

import com.project.payload.request.FlightScheduleRequest;
import com.project.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {

    FlightScheduleResponse crateFlightSchedule(Long userId , FlightScheduleRequest request) throws Exception;

    FlightScheduleResponse getFlightScheduleById(Long id) throws Exception;
    List<FlightScheduleResponse> getFlightScheduleByAirlineId(Long userId);
    FlightScheduleResponse updateFlightSchedule(Long id , FlightScheduleRequest request) throws Exception;
    void deleteFlightSchedule(Long id) throws Exception;
}
