package com.project.controller;

import com.project.payload.request.CityRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.CityResponse;
import com.project.service.impl.CityServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {

    private final CityServiceImpl cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity(
            @Valid
            @RequestBody CityRequest request
    ) throws Exception {

        CityResponse res = cityService.createCity(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(res);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(
            @PathVariable Long id
    ) throws Exception {

        CityResponse res = cityService.getCityById(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(res);

    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CityResponse> cities = cityService.getAllCities(pageable);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cities);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest request
    ) throws Exception {
        CityResponse res = cityService.updateCityById(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(
            @PathVariable Long id
    ) throws Exception {
        cityService.deleteCityById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse("city deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
            ) {
        Pageable pageable = PageRequest.of(page , size);
        Page<CityResponse> res = cityService.searchCities(keyword , pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getAllCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){

        Pageable pageable = PageRequest.of(page , size);
        Page<CityResponse> res = cityService.getCityByCountryCode(countryCode.toUpperCase() , pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);

    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(
            @PathVariable String cityCode
    ){
        boolean res = cityService.cityExist(cityCode.toUpperCase());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

}
