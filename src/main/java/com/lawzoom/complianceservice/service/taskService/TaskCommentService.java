package com.lawzoom.complianceservice.service.taskService;

import com.lawzoom.complianceservice.model.comments.TaskComments;

import java.util.List;

public interface TaskCommentService {

    /**
     * Add a new comment to a task.
     *
     * @param taskId      ID of the task
     * @param userId      ID of the user adding the comment
     * @param commentText The comment text
     */
    void addComment(Long taskId, Long userId, String commentText);

    /**
     * Get all comments for a specific task.
     *
     * @param taskId ID of the task
     * @return List of TaskComments
     */
    List<TaskComments> getCommentsByTask(Long taskId);

    /**
     * Delete a comment by its ID.
     *
     * @param commentId ID of the comment to delete
     */
    void deleteComment(Long commentId);
}
