package com.project.payload.request;

import com.project.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    @Size(max = 16)
    private String flightNumber;

    private Long aircraftId;

    private Long airlineId;

    @NotNull(message = "departure airport id is required")
    private Long departureAirportId;

    @NotNull(message = "arrival airport id is required")
    private Long arrivalAirportId;

    private FlightStatus status;
}
