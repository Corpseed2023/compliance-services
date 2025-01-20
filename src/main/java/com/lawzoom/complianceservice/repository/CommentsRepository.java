package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.comments.MileStoneComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<MileStoneComments, Long> {
    List<MileStoneComments> findByMilestoneId(Long milestoneId);
}
