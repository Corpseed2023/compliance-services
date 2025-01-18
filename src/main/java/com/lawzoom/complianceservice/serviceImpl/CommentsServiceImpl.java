package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.model.Comments;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.CommentsRepository;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comments addComment(Long milestoneId, Long userId, String commentText) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + milestoneId));

        // Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create and Save Comment
        Comments comment = new Comments();
        comment.setCommentText(commentText);
        comment.setUser(user);
        comment.setMilestone(milestone);
        comment.setCreatedAt(new Date());

        return commentsRepository.save(comment);
    }

    @Override
    public List<Comments> getCommentsByMilestone(Long milestoneId) {
        // Validate Milestone
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new IllegalArgumentException("Milestone not found with ID: " + milestoneId);
        }

        // Fetch Comments
        return commentsRepository.findByMilestoneId(milestoneId);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));

        commentsRepository.delete(comment);
    }
}
