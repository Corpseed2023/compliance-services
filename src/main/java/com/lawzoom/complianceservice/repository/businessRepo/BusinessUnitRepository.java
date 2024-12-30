package com.lawzoom.complianceservice.repository.businessRepo;



import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit,Long>

{
    Optional<BusinessUnit> findById(Long businessUnitId);

    @Query("SELECT COUNT(DISTINCT bu.state.id) FROM BusinessUnit bu WHERE bu.gstDetails.company.id = :companyId")
    Long countDistinctStatesByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT COUNT(bu) FROM BusinessUnit bu WHERE bu.gstDetails.company.id = :companyId")
    Long countByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT bu FROM BusinessUnit bu WHERE bu.gstDetails = :gstDetails AND bu.isDeleted = false")
    List<BusinessUnit> findAllByGstDetails(@Param("gstDetails") GstDetails gstDetails);

}