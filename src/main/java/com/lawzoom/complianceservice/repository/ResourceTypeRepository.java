package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.user.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long> {


}