package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByMilestoneId(Long milestoneId);
}
