package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.model.Comments;

import java.util.List;

public interface CommentsService {

    /**
     * Add a comment to a milestone.
     */
    Comments addComment(Long milestoneId, Long userId, String commentText);

    /**
     * Fetch all comments for a specific milestone.
     */
    List<Comments> getCommentsByMilestone(Long milestoneId);

    /**
     * Delete a comment by its ID.
     */
    void deleteComment(Long commentId);
}
