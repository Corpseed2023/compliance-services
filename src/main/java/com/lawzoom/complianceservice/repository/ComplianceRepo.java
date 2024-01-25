package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplianceRepo extends JpaRepository<Compliance, Long> {
    List<Compliance> findByBusinessUnitId(Long businessUnitId);

    List<Compliance> findAllByBusinessUnitId(Long businessUnitId);

    Compliance findByIdAndBusinessUnitId(Long complianceId, Long businessUnitId);

    List<Compliance> findByCompanyId(Long companyId);

    Optional<Compliance> findByIdAndCompanyId(Long complianceId, Long companyId);

    Compliance findComplianceById(Long complianceId);

    List<Compliance> findByCompanyIdAndBusinessUnitId(Long companyId, Long businessUnitId);

    List<Compliance> findByCompanyIdAndBusinessUnitIdAndTeamId(Long companyId, Long businessUnitId, Long teamId);

    @Query("SELECT c.companyId, c.businessUnitId, COUNT(c.id) " +
            "FROM Compliance c " +
            "WHERE c.companyId IS NOT NULL AND c.businessUnitId IS NOT NULL " +
            "GROUP BY c.companyId, c.businessUnitId")
    List<Object[]> countCompliancePerCompanyAndBusinessUnit();
}
