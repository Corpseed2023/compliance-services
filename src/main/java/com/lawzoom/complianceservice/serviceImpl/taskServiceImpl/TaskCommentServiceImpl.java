package com.lawzoom.complianceservice.serviceImpl.taskServiceImpl;

import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.comments.TaskComments;
import com.lawzoom.complianceservice.model.taskModel.Task;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.taskRepo.TaskCommentRepository;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.service.taskService.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addComment(Long taskId, Long userId, String commentText) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        TaskComments comment = new TaskComments();
        comment.setTask(task);
        comment.setUser(user);
        comment.setCommentText(commentText);
        taskCommentRepository.save(comment);
    }

    @Override
    public List<TaskComments> getCommentsByTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId));
        return taskCommentRepository.findByTask(task);
    }

    @Override
    public void deleteComment(Long commentId) {
        TaskComments comment = taskCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + commentId));
        taskCommentRepository.delete(comment);
    }
}
