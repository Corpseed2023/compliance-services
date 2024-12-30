package com.lawzoom.complianceservice.repository.businessRepo;


import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndustryCategoryRepository extends JpaRepository<IndustryCategory,Long> {

    List<IndustryCategory> findByIsEnableTrueAndIsDeletedFalse();
}
