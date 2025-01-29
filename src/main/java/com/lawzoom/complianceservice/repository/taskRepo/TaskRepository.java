package com.lawzoom.complianceservice.repository.taskRepo;


import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByMilestone(MileStone milestone);

    List<Task> findByManager(User manager);

    List<Task> findByAssignee(User assignee);


    Page<Task> findByMilestone_Subscriber(Subscriber subscriber, Pageable pageable);

}
