package com.project.service;

import com.project.payload.request.CityRequest;
import com.project.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CityService {

    CityResponse createCity(CityRequest request) throws Exception;
    CityResponse getCityById(Long id) throws Exception;

    CityResponse updateCityById(Long id , CityRequest request) throws Exception;
    void deleteCityById(Long id) throws Exception;
    Page<CityResponse> getAllCities(Pageable pageable);

    Page<CityResponse> searchCities(String keyword , Pageable pageable);
    Page<CityResponse> getCityByCountryCode(String countryCode , Pageable pageable);

    boolean cityExist(String cityCode);
}
