package com.lawzoom.complianceservice.serviceimpl.complianceSubTaskServiceImpl;

import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskRequest;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.repository.ComplianceSubTaskRepo;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceSubTaskService.ComplianceSubTaskService;
import com.lawzoom.complianceservice.utility.CommonUtil;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceSubTaskServiceImpl implements ComplianceSubTaskService {

    @Autowired
    private ComplianceSubTaskRepo subTaskRepo;

    @Autowired
    private ComplianceTaskRepository taskRepository;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity fetchSubTaskByTask(Long taskId) {

        Optional<ComplianceTask> complianceTask=this.taskRepository.findById(taskId);

        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        List<ComplianceSubTask> complianceSubTaskList=this.subTaskRepo.findByComplianceTask(complianceTask);
        if(complianceSubTaskList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceSubTaskList.stream()
                .map(t->this.responseMapper.mapToComplianceSubTask(t)));
    }


    public ResponseEntity saveSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
        Optional<ComplianceTask> complianceTask = taskRepository.findById(taskId);

        if (complianceTask.isEmpty()) {
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
        }

        ComplianceSubTask findSubTask = subTaskRepo.findByComplianceTaskAndSubTaskName(complianceTask, subTaskRequest.getSubTaskName());

        if (findSubTask != null) {
            return new ResponseEntity().badRequest("Compliance Sub-Task Already Exists !!");
        }

        ComplianceSubTask saveSubTask = new ComplianceSubTask();
        saveSubTask.setSubTaskName(subTaskRequest.getSubTaskName());
        saveSubTask.setTimelineValue(subTaskRequest.getTimelineValue());
        saveSubTask.setTimelineType(subTaskRequest.getTimelineType());
        saveSubTask.setStatus(subTaskRequest.getStatus());
        saveSubTask.setApprovalState(subTaskRequest.getApprovalState());
        saveSubTask.setApplicableZone(subTaskRequest.getApplicableZone());
        saveSubTask.setEnable(true);
        saveSubTask.setCreatedAt(CommonUtil.getDate());
        saveSubTask.setStartDate(subTaskRequest.getStartDate());
        saveSubTask.setDueDate(subTaskRequest.getDueDate());
        saveSubTask.setCompletedDate(subTaskRequest.getCompletedDate());
        saveSubTask.setReporterUserId(subTaskRequest.getReporterUserId());
        saveSubTask.setAssigneeUserId(subTaskRequest.getAssigneeUserId());
        saveSubTask.setCriticality(subTaskRequest.getCriticality());
        saveSubTask.setUpdatedAt(CommonUtil.getDate());
        saveSubTask.setDescription(subTaskRequest.getDescription());
        saveSubTask.setComplianceTask(complianceTask.get());

        saveSubTask = subTaskRepo.save(saveSubTask);

        if (saveSubTask == null) {
            return new ResponseEntity().internalServerError();
        }

        return new ResponseEntity().ok();
    }


    @Override
    public ResponseEntity updateSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
        Optional<ComplianceTask> complianceTask = this.taskRepository.findById(taskId);
        if (!complianceTask.isPresent()) {
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
        }

        ComplianceSubTask findSubTask = this.subTaskRepo.findByComplianceTaskAndSubTaskNameAndId(complianceTask, subTaskRequest.getSubTaskName(), subTaskRequest.getId());
        if (findSubTask != null) {
            return new ResponseEntity().badRequest("Compliance Sub-Task Already exists !!");
        }

        ComplianceSubTask updateSubTask = new ComplianceSubTask();
        updateSubTask.setId(subTaskRequest.getId());
        updateSubTask.setSubTaskName(subTaskRequest.getSubTaskName());
        updateSubTask.setTimelineValue(subTaskRequest.getTimelineValue());
        updateSubTask.setTimelineType(subTaskRequest.getTimelineType());
        updateSubTask.setStatus(subTaskRequest.getStatus());
        updateSubTask.setApprovalState(subTaskRequest.getApprovalState());
        updateSubTask.setApplicableZone(subTaskRequest.getApplicableZone());
        updateSubTask.setEnable(subTaskRequest.isEnable());
        updateSubTask.setCreatedAt(subTaskRequest.getCreatedAt());
        updateSubTask.setStartDate(subTaskRequest.getStartDate());
        updateSubTask.setDueDate(subTaskRequest.getDueDate());
        updateSubTask.setCompletedDate(subTaskRequest.getCompletedDate());
        updateSubTask.setReporterUserId(subTaskRequest.getReporterUserId());
        updateSubTask.setAssigneeUserId(subTaskRequest.getAssigneeUserId());
        updateSubTask.setCriticality(subTaskRequest.getCriticality());
        updateSubTask.setUpdatedAt(CommonUtil.getDate());
        updateSubTask.setDescription(subTaskRequest.getDescription());
        updateSubTask.setComplianceTask(complianceTask.get());

        ComplianceSubTask updatedSubTask = this.subTaskRepo.save(updateSubTask);
        if (updatedSubTask == null) {
            return new ResponseEntity().internalServerError();
        }

        return new ResponseEntity().ok();
    }


    @Override
    public ResponseEntity fetchSubTaskById(Long taskId, Long subTaskId) {
        Optional<ComplianceTask> complianceTask=this.taskRepository.findById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask complianceSubTask=this.subTaskRepo.findSubTaskByTaskAndId(complianceTask,subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToComplianceSubTaskResponse(complianceSubTask));
    }

    @Override
    public ResponseEntity deleteSubTaskById(Long taskId, Long subTaskId) {
        ComplianceTask complianceTask=this.taskRepository.fetchComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask complianceSubTask=this.subTaskRepo.findSubTaskByTaskAndId(complianceTask,subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");

        boolean deleteSubTask=this.subTaskRepo.delete(complianceSubTask);
        if(!deleteSubTask)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
