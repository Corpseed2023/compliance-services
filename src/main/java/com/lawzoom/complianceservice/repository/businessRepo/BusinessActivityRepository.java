package com.lawzoom.complianceservice.repository.businessRepo;

import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessActivityRepository extends JpaRepository<BusinessActivity,Long> {


    @Query("SELECT bsa from BusinessActivity bsa where bsa.isEnable = true and bsa.isDeleted = false and bsa.industrySubCategory.id =:industrySubCategoryId")
    List<BusinessActivity> findByIsEnableTrueAndIsDeletedFalse(@Param("industrySubCategoryId")Long industrySubCategoryId);

    Optional<BusinessActivity> findById(Long id);

    @Query("SELECT bsa FROM BusinessActivity bsa " +
            "WHERE bsa.isEnable = true AND bsa.isDeleted = false " +
            "AND (:searchTerm IS NULL OR LOWER(bsa.businessActivityName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<BusinessActivity> findActiveBusinessActivities(@Param("searchTerm") String searchTerm);



}
