package com.project.service.impl;

import com.project.mapper.FlightInstanceMapper;
import com.project.model.Flight;
import com.project.model.FlightInstance;
import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightInstanceResponse;
import com.project.repository.FlightInstanceRepository;
import com.project.repository.FlightRepository;
import com.project.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository insRepo;
    private final FlightRepository flightRepo;

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception {
        // todo : we are not going to get the airlineId from frontend we get the userid(ownerId) in header from there we need to get the airlineId

        Flight flight = flightRepo.findById(request.getFlightId()).orElseThrow(
                () -> new Exception("no flight found")
        );

        //todo : dummy aircraft we need service to service communication here
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .build();

        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance saved = insRepo.save(flightInstance);

        // todo : create seat instances

        return convertFlightInstanceResponse(flightInstance);
    }

    @Override
    public FlightInstanceResponse updateFlightInstanceInstance(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance existing = insRepo.findById(id).orElseThrow(
                () -> new Exception("no flight instance found")
        );

        FlightInstanceMapper.updateEntity(request, existing);
        return convertFlightInstanceResponse(insRepo.save(existing));
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        FlightInstance flightInstance = insRepo.findById(id).orElseThrow(
                () -> new Exception("no flight instance found")
        );
        return convertFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getFlightInstanceByAirlineId(Long airlineId, Long departureAirportId, Long arrivalAirportId, Long flightId, LocalDate onDate, Pageable pageable) {

        // todo : we are not going to get the airlineId from frontend we get the userid(ownerId) in header from there we need to get the airlineId

        LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
        LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;

        return insRepo.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, start, end, pageable)
                .map(this::convertFlightInstanceResponse);
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance flightInstance = insRepo.findById(id).orElseThrow(
                () -> new Exception("no flight instance found")
        );

        insRepo.delete(flightInstance);

    }

    private FlightInstanceResponse convertFlightInstanceResponse(FlightInstance flightInstance) {
        AirlineResponse airline = AirlineResponse.builder()
                .id(flightInstance.getAirlineId())
                .build();

        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightInstance.getDepartureAirportId())
                .build();

        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightInstance.getArrivalAirportId())
                .build();

        AircraftResponse aircraftResponse = AircraftResponse.builder()
                .id(flightInstance.getFlight().getAircraftId())
                .build();

        return FlightInstanceMapper.toResponse(flightInstance, aircraftResponse, airline, departureAirport, arrivalAirport);
    }
}
