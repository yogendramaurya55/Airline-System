package com.project.service.impl;

import com.project.mapper.CityMapper;
import com.project.model.City;
import com.project.payload.request.CityRequest;
import com.project.payload.response.CityResponse;
import com.project.repository.CityRepository;
import com.project.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        if( request.getCityCode() != null &&  cityRepo.existsByCityCodeAndIdNot(request.getCityCode() , id)){
            throw new Exception("City with provided code already exists");
        }

        City updatedCity = CityMapper.updateEntity(city , request);
        City res = cityRepo.save(updatedCity);

        return CityMapper.toResponse(res);

    }

    @Override
    public void deleteCityById(Long id) throws Exception{

        City city = cityRepo.findById(id).orElseThrow(
                () -> new Exception("City not exists with provided id")
        );

        cityRepo.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {

        Page<City> cities = cityRepo.findAll(pageable);

        return cities.map(CityMapper::toResponse);

    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {

        Page<City> cities = cityRepo.searchByKeyword(keyword , pageable);

        return cities.map(CityMapper::toResponse);

    }

    @Override
    public Page<CityResponse> getCityByCountryCode(String countryCode, Pageable pageable) {

        Page<City> cities = cityRepo.findByCountryCodeIgnoreCase(countryCode , pageable);

        return cities.map(CityMapper::toResponse);

    }

    @Override
    public boolean cityExist(String cityCode) {
        return cityRepo.existsByCityCode(cityCode);
    }

}
