package com.project.payload.request;

import com.project.enums.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineRequest {

    @NotBlank(message = "iataCode is mandatory")
    @Size(min = 2 , max = 2 , message = "IATA Code must be exactly 2 characters")
    private String iataCode;

    @NotBlank(message = "icao Code is mandatory")
    @Size(min = 3 , max = 3 , message = "ICAO code must be exactly 3 characters")
    private String icaoCode;

    @NotBlank(message = "airline name is mandatory")
    private String name;

    private String alias;

    private String logoUrl;

    private String website;

    private AirlineStatus status;

    private String alliance;

    private Long headquartersCityId;

    private String supportEmail;
    private String supportPhone;
    private String supportHours;
}
