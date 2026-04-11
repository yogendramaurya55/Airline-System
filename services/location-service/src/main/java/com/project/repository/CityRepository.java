package com.project.repository;

import com.project.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City , Long> {

    boolean existsByCityCode(String cityCode);
    boolean existsByCityCodeAndIdNot(String cityCode , Long id);
    Page<City> findByCountryCodeIgnoreCase(String countryCode , Pageable pageable);

    @Query("""
            SELECT c FROM City c
            WHERE LOWER(c.name) like lower(concat('%', :keyword , '%'))
            OR LOWER(c.cityCode) like lower(concat('%', :keyword , '%'))
            OR LOWER(c.countryCode) like lower(concat('%', :keyword , '%'))
            OR LOWER(c.countryName) like lower(concat('%', :keyword , '%'))
            OR LOWER(c.regionCode) like lower(concat('%', :keyword , '%'))
            """)
    Page<City> searchByKeyword(@Param("keyword") String keyword , Pageable pageable);
}
