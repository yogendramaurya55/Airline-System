package com.project.service.impl;

import com.project.mapper.CityMapper;
import com.project.model.City;
import com.project.payload.request.CityRequest;
import com.project.payload.response.CityResponse;
import com.project.repository.CityRepository;
import com.project.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepo;

    @Override
    public CityResponse createCity(CityRequest request) throws Exception {

        if(cityRepo.existsByCityCode(request.getCityCode())){
            throw new Exception("city with given code already exits");
        }

        City city = CityMapper.toEntity(request);
        City res = cityRepo.save(city);

        return CityMapper.toResponse(res);

    }

    @Override
    public CityResponse getCityById(Long id) throws Exception{

        City city = cityRepo.findById(id).orElseThrow(
                () -> new Exception("City not exists with id")
        );

        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCityById(Long id, CityRequest request) throws Exception {

        City city = cityRepo.findById(id).orElseThrow(
                () -> new Exception("City not Exists with  id")
        );

        City updatedCity = CityMapper.updateEntity(city , request);
        return CityMapper.toResponse(updatedCity);

    }

    @Override
    public void deleteCityById(Long id) {

    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return null;
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CityResponse> getCityByCountryCode(String countryCode, Pageable pageable) {
        return null;
    }

    @Override
    public boolean cityExist(String cityName) {
        return false;
    }

    @Override
    public boolean validateCityCode(String cityCode) {
        return false;
    }
}
