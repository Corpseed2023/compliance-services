package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.documentModel.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // You can add custom query methods here if needed
}
