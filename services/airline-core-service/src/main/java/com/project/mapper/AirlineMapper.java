package com.project.mapper;


import com.project.embeddable.Support;
import com.project.enums.AirlineStatus;
import com.project.model.Airline;
import com.project.payload.request.AirlineRequest;
import com.project.payload.response.AirlineDropdownItem;
import com.project.payload.response.AirlineResponse;

public class AirlineMapper {

    public static Airline getEntity(AirlineRequest request, Long ownerId) {

        if (request == null) return null;

        Airline airline = Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .alliance(request.getAlliance())
                .headquartersCityId(request.getHeadquartersCityId())
                .ownerId(ownerId)
                .build();


        Support support = Support.builder()
                .email(request.getSupportEmail())
                .phone(request.getSupportPhone())
                .hours(request.getSupportHours())
                .build();

        airline.setSupport(support);

        return airline;
    }


    public static AirlineResponse getResponse(Airline entity) {

        if (entity == null) {
            return null;
        }

        return AirlineResponse.builder()
                .id(entity.getId())
                .iataCode(entity.getIataCode())
                .icaoCode(entity.getIcaoCode())
                .name(entity.getName())
                .alias(entity.getAlias())
                .logoUrl(entity.getLogoUrl())
                .website(entity.getWebsite())
                .status(entity.getStatus())
                .alliance(entity.getAlliance())
                .ownerId(entity.getOwnerId())
                .support(entity.getSupport())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .updatedById(entity.getUpdatedById())
                .build();
    }

    public static Airline updateEntity(Airline airline, AirlineRequest request) {

        if (airline == null || request == null) return null;

        airline.setIataCode(request.getIataCode());
        airline.setIcaoCode(request.getIcaoCode());
        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogoUrl());
        airline.setWebsite(request.getWebsite());
        airline.setStatus(request.getStatus());
        airline.setAlliance(request.getAlliance());
        airline.setHeadquartersCityId(request.getHeadquartersCityId());

        if (airline.getSupport() == null) {
            airline.setSupport(new Support());
        }

        airline.getSupport().setEmail(request.getSupportEmail());
        airline.getSupport().setPhone(request.getSupportPhone());
        airline.getSupport().setHours(request.getSupportHours());

        return airline;
    }

    public static AirlineDropdownItem getAirlineDropdownItem(Airline airline){
        if(airline == null)  return null;

        return AirlineDropdownItem.builder()
                .id(airline.getId())
                .name(airline.getName())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .logoUrl(airline.getLogoUrl())
                .build();
    }
}
