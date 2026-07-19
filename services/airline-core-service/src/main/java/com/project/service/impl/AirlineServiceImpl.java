package com.project.service.impl;

import com.project.enums.AirlineStatus;
import com.project.mapper.AirlineMapper;
import com.project.model.Airline;
import com.project.payload.request.AirlineRequest;
import com.project.payload.response.AirlineDropdownItem;
import com.project.payload.response.AirlineResponse;
import com.project.repository.AirlineRepository;
import com.project.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {


    private final AirlineRepository airlineRepo;

    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception {
        Optional<Airline> dbEntity = airlineRepo.findByOwnerId(ownerId);
        Optional<Airline> dbAirline = airlineRepo.findByIataCodeAndIcaoCode(request.getIataCode() , request.getIcaoCode());
        if(dbEntity.isPresent() || dbAirline.isPresent()){
            throw new Exception("airline already exist with this owner , iataCode and icaoCode");
        }

        Airline airline = AirlineMapper.getEntity(request, ownerId);
        Airline savedAirline = airlineRepo.save(airline);
        return AirlineMapper.getResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline not found with " + ownerId)
        );
        return AirlineMapper.getResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepo.findById(id).orElseThrow(
                () -> new Exception("airline not found with " + id)
        );
        return AirlineMapper.getResponse(airline);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline not found with " + ownerId)
        );

        Airline updatedAirline = AirlineMapper.updateEntity(airline , request);
        Airline savedAirline = airlineRepo.save(updatedAirline);

        return AirlineMapper.getResponse(savedAirline);
    }

    @Override
    public Page<AirlineResponse> getAllAirline(Pageable pageable) {
        return airlineRepo.findAll(pageable).map(AirlineMapper::getResponse);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("airline not found with " + ownerId)
        );

        if(!Objects.equals(airline.getId(), id)){
            throw new Exception("only owner is authorized to delete the airline");
        }

        airlineRepo.delete(airline);

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepo.findById(airlineId).orElseThrow(
                () -> new Exception("no airline found with " + airlineId)
        );
        airline.setStatus(status);
        Airline updatedAirline = airlineRepo.save(airline);
        return AirlineMapper.getResponse(updatedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
         List<Airline> airlines =  airlineRepo.findByStatus(AirlineStatus.ACTIVE);

        return airlines.stream().map(AirlineMapper::getAirlineDropdownItem).toList();
    }
}
