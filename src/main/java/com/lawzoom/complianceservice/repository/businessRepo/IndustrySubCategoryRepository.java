package com.lawzoom.complianceservice.repository.businessRepo;

import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustrySubCategoryRepository extends JpaRepository<IndustrySubCategory, Long> {

    @Query("SELECT isc FROM IndustrySubCategory isc WHERE isc.isEnable = true AND isc.isDeleted = false" +
            " AND isc.industryCategory.id = :industryCategoryId")
    List<IndustrySubCategory> findByIsEnableTrueAndIndustryCategoryId(Long industryCategoryId);

}