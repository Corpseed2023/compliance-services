package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry,Long> {
    Enquiry findByMobile(String mobile);
}
