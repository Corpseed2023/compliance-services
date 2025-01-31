package com.lawzoom.complianceservice.service.mileStoneService;


import com.lawzoom.complianceservice.model.comments.MileStoneComments;

import java.util.List;

public interface MileStoneCommentsService {

    /**
     * Add a comment to a milestone.
     */
    MileStoneComments addComment(Long milestoneId, Long userId, String commentText);

    /**
     * Fetch all comments for a specific milestone.
     */
    List<MileStoneComments> getCommentsByMilestone(Long milestoneId);

    /**
     * Delete a comment by its ID.
     */
    void deleteComment(Long commentId);
}
