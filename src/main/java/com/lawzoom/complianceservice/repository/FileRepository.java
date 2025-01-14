package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.documentModel.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}