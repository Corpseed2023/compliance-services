package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.user.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    @Query("""
        SELECT r FROM Reminder r
        WHERE r.compliance = :compliance AND r.subscriber = :subscriber
    """)
    List<Reminder> findByComplianceAndSubscriber(@Param("compliance") Compliance compliance,
                                                 @Param("subscriber") Subscriber subscriber);
}

