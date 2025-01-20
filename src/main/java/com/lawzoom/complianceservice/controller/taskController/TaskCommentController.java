package com.lawzoom.complianceservice.controller.taskController;


import com.lawzoom.complianceservice.model.comments.TaskComments;
import com.lawzoom.complianceservice.service.taskService.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/comments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskCommentController {

    @Autowired
    private TaskCommentService taskCommentService;

    /**
     * Add a new comment to a task.
     *
     * @param taskId      ID of the task
     * @param userId      ID of the user adding the comment
     * @param commentText The comment text
     * @return ResponseEntity with a success message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestParam Long taskId,
                                             @RequestParam Long userId,
                                             @RequestParam String commentText) {
        taskCommentService.addComment(taskId, userId, commentText);
        return ResponseEntity.ok("Comment added successfully.");
    }

    /**
     * Get all comments for a specific task.
     *
     * @param taskId ID of the task
     * @return ResponseEntity with a list of comments
     */
    @GetMapping("/task")
    public ResponseEntity<List<TaskComments>> getCommentsByTask(@RequestParam Long taskId) {
        List<TaskComments> comments = taskCommentService.getCommentsByTask(taskId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Delete a comment by its ID.
     *
     * @param commentId ID of the comment to delete
     * @return ResponseEntity with a success message
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) {
        taskCommentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully.");
    }
}
