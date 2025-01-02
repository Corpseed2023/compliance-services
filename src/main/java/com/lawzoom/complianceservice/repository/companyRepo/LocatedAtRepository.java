package com.lawzoom.complianceservice.repository.companyRepo;

import com.lawzoom.complianceservice.model.region.LocatedAt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LocatedAtRepository extends JpaRepository<LocatedAt, Long> {

    @Query("SELECT l FROM LocatedAt l WHERE l.isEnable = :isEnable AND l.isDeleted = :isDeleted")
    List<LocatedAt> findAllByIsEnableAndIsDeleted(@Param("isEnable") boolean isEnable, @Param("isDeleted") boolean isDeleted);

    @Query("SELECT l FROM LocatedAt l WHERE l.id = :id AND l.isEnable = true")
    Optional<LocatedAt> findByIdAndIsEnable(@Param("id") Long id);

    @Query("SELECT l.locationName FROM LocatedAt l WHERE l.isEnable = true AND l.isDeleted = false")
    List<String> findLocationNamesByIsEnableAndIsDeleted();

    @Query("SELECT l FROM LocatedAt l WHERE l.locationName = :locationName AND l.isEnable = true AND l.isDeleted = false")
    LocatedAt findByLocationNameAndIsEnableAndIsDeleted(@Param("locationName") String locationName);


}
