package com.project.repository;

import com.project.enums.AirlineStatus;
import com.project.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    @Query("""
            SELECT a
            FROM Airline a
            WHERE a.ownerId = :id
            """)
    Optional<Airline> findByOwnerId(@Param(value = "id") Long id);

    @Query("""
            SELECT a
            FROM Airline a
            WHERE a.status = :status
            """)
    List<Airline> findByStatus(AirlineStatus status);

    @Query("""
           SELECT a
           FROM Airline a
           WHERE a.iataCode = :iataCode AND a.icaoCode = :icaoCode
           """)
    Optional<Airline> findByIataCodeAndIcaoCode(String iataCode , String icaoCode);
}


