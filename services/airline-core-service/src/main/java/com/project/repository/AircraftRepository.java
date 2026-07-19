package com.project.repository;

import com.project.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft , Long> {

    @Query("""
            SELECT a
            FROM Aircraft a
            WHERE a.airline.id = :airlineId
            """)
    List<Aircraft> findByAirlineId( @Param(value = "airlineId") Long airlineId);

    boolean existsByCode(String code);

    @Query("""
            SELECT a
            FROM Aircraft a
            WHERE a.id = :id AND a.airline.id = :airlineId
            """)
    Optional<Aircraft> findByIdAndAirlineId(Long id , Long airlineId);
}
