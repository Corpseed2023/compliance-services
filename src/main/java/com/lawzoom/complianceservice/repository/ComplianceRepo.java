package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepo extends JpaRepository<Compliance, Long> {


    List<Compliance> findByBusinessUnitId(Long businessUnitId);

//    List<Compliance> findAllByBusinessUnitAndIsEnableAndIsDeletedFalse(BusinessUnit businessUnit);
//    List<Compliance> findByBusinessUnitId(Long businessUnitId);
//
//    List<Compliance> findAllByBusinessUnitId(Long businessUnitId);
//
//    Compliance findByIdAndBusinessUnitId(Long complianceId, Long businessUnitId);
//
//    List<Compliance> findByCompanyId(Long companyId);
//
//    Optional<Compliance> findByIdAndCompanyId(Long complianceId, Long companyId);
//
    Compliance findComplianceById(Long complianceId);
//
//    List<Compliance> findByCompanyIdAndBusinessUnitId(Long companyId, Long businessUnitId);
//
//    List<Compliance> findByCompanyIdAndBusinessUnitIdAndTeamId(Long companyId, Long businessUnitId, Long teamId);
//
//    @Query("SELECT c.companyId, c.businessUnitId, COUNT(c.id) " +
//            "FROM Compliance c " +
//            "WHERE c.companyId IS NOT NULL AND c.businessUnitId IS NOT NULL " +
//            "GROUP BY c.companyId, c.businessUnitId")
//    List<Object[]> countCompliancePerCompanyAndBusinessUnit();



//    @Query("SELECT new com.example.dto.CompanyComplianceDTO(c.companyName, g.gstNumber, b.address, COUNT(comp)) " +
//            "FROM Company c " +
//            "JOIN GstDetails g ON g.company.id = c.id " +
//            "JOIN BusinessUnit b ON b.gstDetails.id = g.id " +
//            "LEFT JOIN Compliance comp ON comp.businessUnit.id = b.id " +
//            "WHERE c.isDeleted = false AND c.isEnable = true " +
//            "GROUP BY c.id, g.gstNumber, b.address")
//    List<CompanyComplianceDTO> findCompanyComplianceDetails();


    List<Compliance> findAllByBusinessUnit(BusinessUnit businessUnit);




}
