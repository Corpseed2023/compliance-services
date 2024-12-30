package com.lawzoom.complianceservice.repository.companyRepo;



import com.lawzoom.complianceservice.model.companyModel.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType,Long> {

    @Query("SELECT c FROM CompanyType c WHERE c.isEnable = true AND c.isDeleted = false")
    List<CompanyType> findEnabledAndNotDeletedCompanies();

    @Query("SELECT c FROM CompanyType c WHERE c.isEnable = true AND c.isDeleted = false")
    List<CompanyType> findAllCompanyTypeNames();

    @Query("UPDATE CompanyType c SET c.isDeleted = true, c.updatedAt = CURRENT_TIMESTAMP WHERE c.id = :id")
    void softDeleteById(Long id);

    @Query("SELECT c FROM CompanyType c WHERE c.id = :id AND c.isDeleted = false and c.isEnable =true")
    Optional<CompanyType> findByIdAndNotDeleted(@Param("id") Long id);

}
