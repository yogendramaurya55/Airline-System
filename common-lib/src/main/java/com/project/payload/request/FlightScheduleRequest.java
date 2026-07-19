package com.project.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightScheduleRequest {

    @NotNull(message = "this field is required")
    private  Long flightId;


    @NotNull(message = "this field is required")
    private LocalTime departureTime;

    @NotNull(message = "this field is required")
    private LocalTime arrivalTime;

    @NotNull(message = "this field is required")
    private LocalDate startDate;

    @NotNull(message = "this field is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;

    private Boolean isActive;

}
