package com.project.service;

import com.project.payload.request.CityRequest;
import com.project.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import java.awt.print.Pageable;

public interface CityService {

    CityResponse createCity(CityRequest request) throws Exception;
    CityResponse getCityById(Long id) throws Exception;

    CityResponse updateCityById(Long id , CityRequest request) throws Exception;
    void deleteCityById(Long id);
    Page<CityResponse> getAllCities(Pageable pageable);

    Page<CityResponse> searchCities(String keyword , Pageable pageable);
    Page<CityResponse> getCityByCountryCode(String countryCode , Pageable pageable);

    boolean cityExist(String cityName);
    boolean validateCityCode(String cityCode);
}
