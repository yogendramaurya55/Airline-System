package com.project.repository;

import com.project.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule , Long> {

    @Query("""
            SELECT fs
            FROM FlightSchedule fs
            WHERE flight.airlineId = :airlineId
            """)
    List<FlightSchedule> findByAirlineId(@Param("airlineId") Long airlineId);
}
