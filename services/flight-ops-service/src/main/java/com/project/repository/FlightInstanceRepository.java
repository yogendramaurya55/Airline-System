package com.project.repository;

import com.project.model.FlightInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FlightInstanceRepository extends JpaRepository<FlightInstance , Long> {

    @Query("""
            SELECT f
            FROM FlightInstance f
            WHERE f.airlineId = :airlineId
            AND (departureAirportId IS null OR f.departureAirportId = :departureAirportId )
            AND (arrivalAirportId IS null OR f.arrivalAirportId = :arrivalAirportId)
            AND (:flightId IS NULL OR f.flight.id = :flightId)
            AND (:dayStart IS NULL OR f.departureDateTime >= :dayStart)
            AND (:dayEnd IS NULL OR f.arrivalDateTime <= :dayEnd)
            """)
    Page<FlightInstance> findByAirlineId(@Param("airlineId") Long airlineId ,
                                         @Param("departureAirportId") Long departureAirportId ,
                                         @Param("arrivalAirportId") Long arrivalAirportId ,
                                         @Param("flightId") Long flightId,
                                         @Param("dayStart") LocalDateTime dayStart,
                                         @Param("dayEnd") LocalDateTime dayEnd,
                                         Pageable pageable);
}
