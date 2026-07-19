package com.project.service;

import com.project.payload.request.AircraftRequest;
import com.project.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest request , Long ownerId) throws Exception;
    AircraftResponse getAircraftById(Long id) throws Exception;
    List<AircraftResponse> listAllAircraftByOwnerId(Long ownerId) throws Exception;
    AircraftResponse updateAircraft(Long id ,AircraftRequest request , Long ownerId) throws Exception;
    void deleteAircraft(Long id , Long ownerId) throws Exception;



}
