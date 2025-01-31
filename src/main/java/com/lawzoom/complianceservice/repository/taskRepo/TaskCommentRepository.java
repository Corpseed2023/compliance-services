package com.lawzoom.complianceservice.repository.taskRepo;

import com.lawzoom.complianceservice.model.comments.TaskComments;
import com.lawzoom.complianceservice.model.taskModel.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComments, Long> {

    /**
     * Find comments by task.
     *
     * @param task Task entity
     * @return List of TaskComments
     */
    List<TaskComments> findByTask(Task task);
}
