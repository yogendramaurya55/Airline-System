package com.project.service.impl;

import com.project.mapper.AirportMapper;
import com.project.model.Airport;
import com.project.model.City;
import com.project.payload.request.AirportRequest;
import com.project.payload.response.AirportResponse;
import com.project.repository.AirportRepository;
import com.project.repository.CityRepository;
import com.project.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepo;
    private final CityRepository cityRepo;

    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {

        if (airportRepo.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with iata code is already exists");
        }

        City city = cityRepo.findById(request.getCityId()).orElseThrow(() -> new Exception("City not found"));
        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepo.save(airport);
        AirportResponse res = AirportMapper.toResponse(savedAirport);
        return res;
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {

        Airport airport = airportRepo.findById(id).orElseThrow(() -> new Exception("airport not found "));
        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {

        List<AirportResponse> airports = airportRepo.findAll().stream().map(AirportMapper::toResponse).toList();

        return airports;
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {

        Airport existingAirport = airportRepo.findById(id).orElseThrow(() -> new Exception("No airport exist with provided id"));

        if (request.getIataCode() != null && !existingAirport.getIataCode().equals(request.getIataCode()) && airportRepo.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with Iata code already exists");
        }

        if(!(cityRepo.findById(request.getCityId()).isPresent())){
            throw new Exception("update request failed !! No city found with provided city id");
        }

        Airport updatedAirport = AirportMapper.updateEntity(existingAirport, request);

        Airport res = airportRepo.save(updatedAirport);

        return AirportMapper.toResponse(res);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport = airportRepo.findById(id).orElseThrow(
                () -> new Exception("airport with provided id not exist")
        );
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {

        return airportRepo.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toResponse)
                .toList();
    }
}
