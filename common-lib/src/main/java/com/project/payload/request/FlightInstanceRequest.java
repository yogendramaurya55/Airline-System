package com.project.payload.request;

import com.project.enums.FlightStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInstanceRequest {

    @NotNull(message = "this is required field")
    private Long flightId;

    private Long scheduleId;

    private Long departureAirportId;

    private Long arrivalAirportId;

    @NotNull(message = "this is required field")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "this is required field")
    private LocalDateTime departureDateTime;

    @NotNull(message = "this is required field")
    @Positive
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;

}
