package com.project.service.impl;

import com.project.mapper.AircraftMapper;
import com.project.mapper.AirlineMapper;
import com.project.model.Aircraft;
import com.project.model.Airline;
import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;
import com.project.payload.response.ApiResponse;
import com.project.repository.AircraftRepository;
import com.project.repository.AirlineRepository;
import com.project.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AirlineRepository airlineRepo;
    private final AircraftRepository aircraftRepo;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline not exist with " + ownerId)
        );

        Aircraft aircraft = AircraftMapper.getEntity(request, airline);
        if (aircraftRepo.existsByCode(aircraft.getCode())) {
            throw new Exception("code already exist with another aircraft");
        }
        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("seating capacity cannot exceed total seats");
        }
        Aircraft savedAircraft = aircraftRepo.save(aircraft);
        return AircraftMapper.getResponse(savedAircraft);
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepo.findById(id).orElseThrow(
                () -> new Exception("no aircraft found")
        );
        return AircraftMapper.getResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwnerId(Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("this owner doesn't have airline")
        );

        return aircraftRepo.findByAirlineId(airline.getId())
                .stream().map(AircraftMapper::getResponse)
                .toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("this owner doesn't have airline")
        );

        Aircraft aircraft = aircraftRepo.findByIdAndAirlineId(id, airline.getId()).orElseThrow(
                () -> new Exception("Aircraft not exist with id")
        );
        if (request.getCode() != null && !aircraft.getCode().equals(request.getCode()) && aircraftRepo.existsByCode(request.getCode())) {
            throw new Exception("code already exist with another aircraft");
        }
        AircraftMapper.updateEntity(aircraft, request, airline);
        return AircraftMapper.getResponse(
                aircraftRepo.save(aircraft)
        );
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("this owner doesn't have airline")
        );

        Aircraft aircraft = aircraftRepo.findByIdAndAirlineId(id, airline.getId()).orElseThrow(
                () -> new Exception("Aircraft not exist with id")
        );

        aircraftRepo.delete(aircraft);
    }
}
