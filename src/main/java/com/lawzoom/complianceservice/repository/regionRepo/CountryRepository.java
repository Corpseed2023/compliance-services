package com.lawzoom.complianceservice.repository.regionRepo;

import com.lawzoom.complianceservice.model.region.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {

    @Query("SELECT c FROM Country c WHERE c.isEnable = true AND c.isDeleted = false")
    List<Country> findAllEnabledCountries();

    // Custom query to find a country by id, enabled and not deleted
    @Query("SELECT c FROM Country c WHERE c.id = :id AND c.isEnable = true AND c.isDeleted = false")
    Optional<Country> findEnabledAndNotDeletedById(Long id);


    Optional<Country> findByCountryName(String countryName);
}