package com.lawzoom.complianceservice.repository.regionRepo;

import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {


    @Query("SELECT c FROM City c WHERE c.states = :state AND c.isEnable = true AND c.isDeleted = false")
    List<City> findEnabledAndNotDeletedCitiesByState(States state);

    @Query("SELECT c FROM City c WHERE c.id = :id AND c.isEnable = true AND c.isDeleted = false")
    Optional<City> findEnabledAndNotDeletedCityById(Long id);

}
