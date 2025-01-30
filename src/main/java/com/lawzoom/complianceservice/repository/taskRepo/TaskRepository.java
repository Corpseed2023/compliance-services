package com.lawzoom.complianceservice.repository.taskRepo;


import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM task WHERE milestone_id = :milestoneId", nativeQuery = true)
    List<Task> findByMilestone(@Param("milestoneId") Long milestoneId);

    List<Task> findByManager(User manager);

    List<Task> findByAssignee(User assignee);


    Page<Task> findBySubscriber(Subscriber subscriber, Pageable pageable);
}
