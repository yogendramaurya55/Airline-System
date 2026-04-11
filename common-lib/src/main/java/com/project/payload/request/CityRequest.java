package com.project.payload.request;

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
public class CityRequest {

    @NotBlank(message = "City name is required")
    @Size(max = 100 , message  = "City name must be under 100 letters")
    private String name;

    @NotBlank(message = "City code is required")
    @Size(max = 10 , message  = "City code must be under 10 letters")
    private String cityCode;

    @NotBlank(message = "Country code is required")
    @Size(max = 5 , message  = "country code must be under 5 letters")
    private String countryCode;

    @NotBlank(message = "Country name is required")
    @Size(max = 100 , message  = "Country name must be under 100 letters")
    private String countryName;

    @Size(max = 10 , message  = "Region code must be under 10 letters")
    private String regionCode;

    @Size(max = 10 , message  = "Time Zone offset must be under 10 letters")
    private String timeZoneOffset;
}
