package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.documentModel.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    @Query("DELETE FROM Document d WHERE d.compliance.id = :complianceId")
    void deleteByComplianceId(@Param("complianceId") Long complianceId);

    void deleteByTaskId(Long id);
}
