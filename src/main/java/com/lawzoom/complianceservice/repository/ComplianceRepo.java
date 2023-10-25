package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplianceRepo extends JpaRepository<Compliance ,Long>

{

//    List<Compliance> findCompliancesByBusinessUnitId(Long businessUnitId);
//
    List<Compliance> findAllByBusinessUnitId(Long businessUnitId);


    List<Compliance> findByCompanyId(Long companyId);
//
//    Compliance updateCompliance(ComplianceRequest complianceRequest, long l, Long businessUnitId);
//
    Compliance findByIdAndBusinessUnitId(Long complianceId, Long businessUnitId);
//
//    List<Compliance> findByCompanyId(Long companyId);
//
//    Optional<Compliance> findByIdAndCompanyId(Long complianceId, Long companyId);
//
//    List<Compliance> findByUserId(Long userId);
//
//    Compliance findComplianceById(Long complianceId);
}
