package com.project.service.impl;

import com.project.enums.FlightStatus;
import com.project.mapper.FlightMapper;
import com.project.model.Flight;
import com.project.payload.request.FlightRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightResponse;
import com.project.repository.FlightRepository;
import com.project.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepo;


    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {
        if (flightRepo.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new Exception("Flight already exists");
        }

        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight saved = flightRepo.save(flight);

        return FlightMapper.toResponse(saved, new AircraftResponse(), new AirlineResponse(), new AirportResponse(), new AirportResponse());
    }

    @Override
    public Page<FlightResponse> getFlightByAirline(Long airlineId, Long departureAirportId, Long arrivalAirportId, Pageable pageable) {

        Page<Flight> flights = flightRepo.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, pageable);

        return flights.map(e -> FlightMapper.toResponse(e, new AircraftResponse(), new AirlineResponse(), new AirportResponse(), new AirportResponse()));
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepo.findById(id).orElseThrow(
                () -> new Exception("no flight found")
        );

        return FlightMapper.toResponse(flight, new AircraftResponse(), new AirlineResponse(), new AirportResponse(), new AirportResponse());
    }

    @Override
    public FlightResponse updateFlight(Long flightId, FlightRequest request) throws Exception {
        Flight existing = flightRepo.findById(flightId).orElseThrow(
                () -> new Exception("no flights found")
        );
        if (request.getFlightNumber() != null &&
                flightRepo.existsByFlightNumberAndIdNot(request.getFlightNumber(), flightId)
        ) {
            throw new Exception("flight already exist");
        }
        FlightMapper.updateEntity(request, existing);
        Flight updated = flightRepo.save(existing);
        return FlightMapper.toResponse(updated, new AircraftResponse(), new AirlineResponse(), new AirportResponse(), new AirportResponse());
    }

    @Override
    public void deleteFlight(Long airlineId, Long id) throws Exception {
        Flight flight = flightRepo.findByAirlineIdAndId(airlineId, id).orElseThrow(
                () -> new Exception("no flight found")
        );

        flightRepo.delete(flight);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) throws Exception {
        Flight flight = flightRepo.findById(id).orElseThrow(
                () -> new Exception("no flight found")
        );
        flight.setStatus(status);
        Flight saved = flightRepo.save(flight);
        return FlightMapper.toResponse(saved, new AircraftResponse(), new AirlineResponse(), new AirportResponse(), new AirportResponse());
    }
}
