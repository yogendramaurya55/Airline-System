package com.project.service.impl;

import com.project.enums.FlightStatus;
import com.project.mapper.FlightMapper;
import com.project.mapper.FlightScheduleMapper;
import com.project.model.Flight;
import com.project.model.FlightSchedule;
import com.project.payload.request.FlightInstanceRequest;
import com.project.payload.request.FlightScheduleRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.AirlineResponse;
import com.project.payload.response.AirportResponse;
import com.project.payload.response.FlightScheduleResponse;
import com.project.repository.FlightInstanceRepository;
import com.project.repository.FlightRepository;
import com.project.repository.FlightScheduleRepository;
import com.project.service.FlightInstanceService;
import com.project.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightRepository flightRepo;
    private final FlightScheduleRepository flightScheduleRepo;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse crateFlightSchedule(Long userId, FlightScheduleRequest request) throws Exception {

        //todo : first fetch the airline using userId using feign client then extract the airline id;

        AirlineResponse airline = AirlineResponse.builder()
                .id(1L)
                .build();


        Flight flight = flightRepo.findById(request.getFlightId()).orElseThrow(
                () -> new Exception("no flight found with provided flight id")
        );

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new Exception("end date is before start date");
        }

        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);

        FlightSchedule saved = flightScheduleRepo.save(flightSchedule);

        //once flight schedule is saved in database we need to create flight instances for each day

        //todo: fetch the aircraft from saved.getFlight().getAircraftId()

        AircraftResponse aircraft = AircraftResponse.builder()
                .id(saved.getFlight().getAircraftId())
                .build();

        List<DayOfWeek> operatingDays = saved.getOperatingDays();

        LocalDate startDate = saved.getStartDate();
        LocalDate endDate = saved.getEndDate();


        for (LocalDate date = startDate;
             !date.isAfter(endDate);
             date = date.plusDays(1)) {

            if (!operatingDays.contains(date.getDayOfWeek())) {
                continue;
            }

            LocalDate arrivalDate = date;

            if (saved.getArrivalTime().isBefore(saved.getDepartureTime())) {
                arrivalDate = arrivalDate.plusDays(1);
            }

            FlightInstanceRequest flightInstancerequest = FlightInstanceRequest.builder()
                    .flightId(flight.getId())
                    .scheduleId(saved.getId())
                    .departureAirportId(saved.getDepartureAirportId())
                    .arrivalAirportId(saved.getArrivalAirportId())
                    .departureDateTime(LocalDateTime.of(date, saved.getDepartureTime()))
                    .arrivalDateTime(LocalDateTime.of(arrivalDate, saved.getArrivalTime()))
                    .totalSeats(aircraft.getTotalSeats())
                    .availableSeats(aircraft.getTotalSeats())
                    .status(FlightStatus.SCHEDULED)
                    .isActive(true)
                    .build();

            flightInstanceService.createFlightInstance(airline.getId(), flightInstancerequest);
        }

        return this.convertFlightScheduleResponse(saved);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepo.findById(id).orElseThrow(
                () -> new Exception("no flight schedule find with provided id")
        );
        return convertFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirlineId(Long userId) {
        //todo : first fetch the airline using userId using feign client then extract the airline id;

        AirlineResponse airline = AirlineResponse.builder()
                .id(1L)
                .build();

        List<FlightSchedule> flightScheduleList = flightScheduleRepo.findByAirlineId(airline.getId());


        return flightScheduleList.stream().map(this::convertFlightScheduleResponse).toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception {
        FlightSchedule existing = flightScheduleRepo.findById(id).orElseThrow(
                () -> new Exception("no flight schedule find with provided id")
        );

        FlightScheduleMapper.updateEntity(request, existing);
        FlightSchedule saved = flightScheduleRepo.save(existing);
        return this.convertFlightScheduleResponse(saved);
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepo.findById(id).orElseThrow(
                () -> new Exception("no flight schedule find with provided id")
        );

        flightScheduleRepo.delete(flightSchedule);

    }

    private FlightScheduleResponse convertFlightScheduleResponse(FlightSchedule fs) {
        AirportResponse departureAirport = new AirportResponse();
        AirportResponse arrivalAirport = new AirportResponse();

        return FlightScheduleMapper.toResponse(fs, arrivalAirport, departureAirport);

    }
}
