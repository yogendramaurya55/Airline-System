package com.project.mapper;

import com.project.model.Airport;
import com.project.payload.request.AirportRequest;
import com.project.payload.response.AirportResponse;

public class AirportMapper {

    public static Airport toEntity(AirportRequest request){

        if(request == null){
            return null;
        }

        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();

    }

    public static AirportResponse toResponse(Airport airport){
        if(airport == null){
            return null;
        }

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static Airport updateEntity(Airport existingAirport , AirportRequest request){

        if(request == null
        || existingAirport == null) return null;

        if(request.getIataCode() != null){
            existingAirport.setIataCode(request.getIataCode());
        }

        if(request.getAddress() != null){
            existingAirport.setAddress(request.getAddress());
        }
        if(request.getName() != null){
            existingAirport.setName(request.getName());
        }

        if(request.getTimeZone() != null){
            existingAirport.setTimeZone(request.getTimeZone());
        }
        if(request.getGeoCode() != null){
            existingAirport.setGeoCode(request.getGeoCode());
        }

        return existingAirport;
    }
}
