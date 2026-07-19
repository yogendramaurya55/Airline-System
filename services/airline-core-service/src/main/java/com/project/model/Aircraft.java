package com.project.model;

import com.project.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String code;

    @Column( nullable = false)
    private String model;

    @Column( nullable = false , length = 50)
    private String manufacturer;

    @Column( nullable = false)
    private Integer seatingCapacity;

    @Column( name = "economy_seats")
    private Integer economySeats=0;

    @Column( name = "premium_economy_seats")
    private Integer premiumEconomySeats=0;

    @Column( name = "business_seats")
    private Integer businessSeats=0;

    @Column( name = "first_class_seats")
    private Integer firstClassSeats=0;

    @Column( name = "cruising_speed_kmh")
    private Integer cruisingSpeedAKmh;

    private Integer rangeKm;
    private Integer maxAltitudeFt;

    @Column( name = "year_of_manufacture")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column( name = "status" , nullable = false , length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    private Boolean isAvailable = true;

    @ManyToOne
    private Airline airline;

    private Long currentAirportId;

    @CreatedDate
    @Column( name = "created_at" , updatable = false , nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column( name = "updated_at" , nullable = false)
    private Instant updatedAt;

    public Integer getTotalSeats(){
        return economySeats + businessSeats + premiumEconomySeats + firstClassSeats;
    }

    public boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status) &&
                Boolean.TRUE.equals(isAvailable);
    }

    //if the next maintenance date is left less than two weeks this function return true
    public boolean requiresMaintenance(){
        return nextMaintenanceDate != null &&
                nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }

}
