package com.lawzoom.complianceservice.controller;

import com.lawzoom.complianceservice.model.Comments;
import com.lawzoom.complianceservice.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/comments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    /**
     * Add a new comment to a milestone.
     *
     * @param milestoneId ID of the milestone
     * @param userId      ID of the user adding the comment
     * @param commentText The comment text
     * @return ResponseEntity with a success message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestParam Long milestoneId,
                                             @RequestParam Long userId,
                                             @RequestParam String commentText) {
        commentsService.addComment(milestoneId, userId, commentText);
        return ResponseEntity.ok("Comment added successfully.");
    }

    /**
     * Get all comments for a specific milestone.
     *
     * @param milestoneId ID of the milestone
     * @return ResponseEntity with a list of comments
     */
    @GetMapping("/milestone/")
    public ResponseEntity<List<Comments>> getCommentsByMilestone(@RequestParam Long milestoneId) {
        List<Comments> comments = commentsService.getCommentsByMilestone(milestoneId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Delete a comment by its ID.
     *
     * @param commentId ID of the comment to delete
     * @return ResponseEntity with a success message
     */
    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId) {
        commentsService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully.");
    }
}
