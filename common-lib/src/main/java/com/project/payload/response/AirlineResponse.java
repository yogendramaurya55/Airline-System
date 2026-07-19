package com.project.payload.response;

import com.project.embeddable.Support;
import com.project.enums.AirlineStatus;
import com.project.payload.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirlineResponse {

    private Long id;

    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private long ownerId;
    private UserDTO owner;
    private Long updatedById;

    private CityResponse headquartersCity;
    private Support support;

    private Instant createdAt;

    private Instant updatedAt;
}
