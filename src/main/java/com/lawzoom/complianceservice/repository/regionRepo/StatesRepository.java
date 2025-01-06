package com.lawzoom.complianceservice.repository.regionRepo;



import com.lawzoom.complianceservice.model.region.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatesRepository extends JpaRepository<States,Long>

{
    @Query("SELECT s FROM States s WHERE s.isEnable = true AND s.isDeleted = false AND s.country.id = :countryId")
    List<States> findAllEnabledAndNotDeletedStates(@Param("countryId") Long countryId);

    @Query("SELECT s FROM States s WHERE s.isEnable = true AND s.isDeleted = false and s.id =:id")
    States findByEnabledAndNotDeleted(Long id);

    @Query("SELECT c.id, c.cityName FROM City c WHERE c.states = :state")
    List<Object[]> findCitiesIdAndNameByState(States state);



}