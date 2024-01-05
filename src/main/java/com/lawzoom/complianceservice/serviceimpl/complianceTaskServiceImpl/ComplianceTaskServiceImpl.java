package com.lawzoom.complianceservice.serviceimpl.complianceTaskServiceImpl;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;

import com.lawzoom.complianceservice.services.complianceTaskService.ComplianceTaskService;
import com.lawzoom.complianceservice.utility.CommonUtil;
//import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplianceTaskServiceImpl implements ComplianceTaskService {

    @Autowired
    private ComplianceRepo complianceRepo;

    @Autowired
    private  ComplianceTaskRepository complianceTaskRepository;

    @Override
    public ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId,
                                           Long companyId, Long businessUnitId) {
        validateRequestParameters(complianceId);

        Optional<Compliance> complianceData = complianceRepo.findById(complianceId);

        ComplianceTask savedTask = complianceData.map(compliance ->
                        complianceTaskRepository.save(mapRequestToEntity(taskRequest, compliance,companyId,businessUnitId)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance not found"));

        return mapEntityToResponse(savedTask);
    }

    private void validateRequestParameters(Long complianceId) {
        if (complianceId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compliance ID cannot be null");
        }
    }

    // Helper method to map request DTO to entity
    private ComplianceTask mapRequestToEntity(ComplianceTaskRequest request, Compliance compliance ,
                                              Long businessUnitId , Long companyId) {
        ComplianceTask task = new ComplianceTask();
        task.setTaskName(request.getTaskName());
        task.setDescription(request.getDescription());
        task.setTimelineValue(request.getTimelineValue());
        task.setTimelineType(request.getTimelineType());
        task.setStatus(request.getStatus());
        task.setApprovalState(request.getApprovalState());
        task.setApplicableZone(request.getApplicableZone());
        task.setCriticality(request.getCriticality());
        task.setReporterUserId(request.getReporterUserId());
        task.setAssigneeUserId(request.getAssigneeUserId());
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setCompletedDate(request.getCompletedDate());
        task.setCreatedAt(request.getCreatedAt());
        task.setUpdatedAt(request.getUpdatedAt());
        task.setEnable(request.isEnable());
        task.setCompanyId(companyId);
        task.setBusinessUnitId(businessUnitId);
//        task.setCompanyId(request.getCompanyId());
//        task.setBusinessUnitId(request.getBusinessUnitId());
//        task.setBusinessActivityId(request.getBusinessActivityId());
        task.setCompliance(compliance);

        return task;
    }

    private ComplianceTaskResponse mapEntityToResponse(ComplianceTask task) {
        ComplianceTaskResponse response = new ComplianceTaskResponse();
        response.setId(task.getId());
        response.setTaskName(task.getTaskName());
        response.setDescription(task.getDescription());
        response.setTimelineValue(task.getTimelineValue());
        response.setTimelineType(task.getTimelineType());
        response.setStatus(task.getStatus());
        response.setApprovalState(task.getApprovalState());
        response.setApplicableZone(task.getApplicableZone());
        response.setCriticality(task.getCriticality());
        response.setReporterUserId(task.getReporterUserId());
        response.setAssigneeUserId(task.getAssigneeUserId());
        response.setStartDate(task.getStartDate());
        response.setDueDate(task.getDueDate());
        response.setCompletedDate(task.getCompletedDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setEnable(task.isEnable());
        response.setCompanyId(task.getCompanyId());
        response.setBusinessUnitId(task.getBusinessUnitId());
        response.setBusinessActivityId(task.getBusinessActivityId());

        // Set other fields as needed

        return response;
    }

    @Override
    public ResponseEntity<?> deleteTaskById(Long complianceId, Long taskId) {

        Compliance compliance = this.complianceRepo.findComplianceById(complianceId);
        if (compliance == null)
//            return ResponseEntity.badRequest().body("Compliance Not Found !!");
            return new ResponseEntity().badRequest("Compliance Not Found !!");


        ComplianceTask complianceTask = this.complianceTaskRepository.findTaskByComplianceAndId(compliance, taskId);
        if (complianceTask == null)
//            return ResponseEntity.badRequest().body("Compliance Task Not Found !!");

            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        try {

            this.complianceTaskRepository.delete(complianceTask);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete compliance task");
            return new ResponseEntity().internalServerError().badRequest("Failed to delete compliance task");

        }
    }

    @Override
    public ResponseEntity updateTask(ComplianceTaskRequest taskRequest, Long complianceId) {
        return null;
    }

    @Override
    public ResponseEntity fetchTaskById(Long complianceId, Long taskId) {
        return null;
    }


    @Override
    public List<ComplianceTaskResponse> getAllTaskByComplianceId(Long complianceId) {
        // Assuming you have a method in the repository to get tasks by complianceId
        List<ComplianceTask> complianceTasks = complianceTaskRepository.findByComplianceId(complianceId);

        // Map the ComplianceTask entities to ComplianceTaskResponse DTOs
        List<ComplianceTaskResponse> taskResponses = complianceTasks.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());

        return taskResponses;
    }

    private ComplianceTaskResponse mapToResponseDto(ComplianceTask complianceTask) {
        // Map the fields from ComplianceTask entity to ComplianceTaskResponse DTO
        ComplianceTaskResponse response = new ComplianceTaskResponse();
        response.setId(complianceTask.getId());
        response.setTaskName(complianceTask.getTaskName());
        response.setDescription(complianceTask.getDescription());
        response.setTimelineValue(complianceTask.getTimelineValue());
        response.setTimelineType(complianceTask.getTimelineType());
        response.setStatus(complianceTask.getStatus());
        response.setApprovalState(complianceTask.getApprovalState());
        response.setApplicableZone(complianceTask.getApplicableZone());
        response.setCriticality(complianceTask.getCriticality());
        response.setReporterUserId(complianceTask.getReporterUserId());
        response.setAssigneeUserId(complianceTask.getAssigneeUserId());
        response.setStartDate(complianceTask.getStartDate());
        response.setDueDate(complianceTask.getDueDate());
        response.setCompletedDate(complianceTask.getCompletedDate());
        response.setCreatedAt(complianceTask.getCreatedAt());
        response.setUpdatedAt(complianceTask.getUpdatedAt());
        response.setEnable(complianceTask.isEnable());
        response.setCompanyId(complianceTask.getCompanyId());
        response.setBusinessUnitId(complianceTask.getBusinessUnitId());
        response.setBusinessActivityId(complianceTask.getBusinessActivityId());

        return response;
    }
}
