package com.lawzoom.complianceservice.serviceimpl.complianceTaskServiceImpl;

import com.lawzoom.complianceservice.dto.TaskResponse;
import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.feignClient.AuthenticationFeignClient;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceTaskService.ComplianceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComplianceTaskServiceImpl implements ComplianceTaskService {

    @Autowired
    private ComplianceRepo complianceRepo;

    @Autowired
    private  ComplianceTaskRepository complianceTaskRepository;


    @Autowired
    private AuthenticationFeignClient authenticationFeignClient;



    @Override
    public ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId,
                                           Long companyId, Long businessUnitId,Long taskCreatedBy) {

        // Fetch user information using Feign Client
        UserResponse userRequestData = authenticationFeignClient.getUserId(taskCreatedBy);
        List<String> roles = userRequestData.getRoles();

        if (roles == null || !(roles.contains("SUPER_ADMIN") || roles.contains("ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role");
        }

        if (complianceId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compliance ID cannot be null");
        }
        Optional<Compliance> complianceData = complianceRepo.findById(complianceId);

        ComplianceTask savedTask = complianceData.map(compliance ->
                        complianceTaskRepository.save(mapRequestToEntity(taskRequest,
                                compliance,companyId,businessUnitId,taskCreatedBy)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance not found"));

        return mapEntityToResponse(savedTask);
    }

    private ComplianceTask mapRequestToEntity(ComplianceTaskRequest request, Compliance compliance ,
                                              Long businessUnitId , Long companyId,Long taskCreatedBy) {
        ComplianceTask task = new ComplianceTask();
        task.setTaskName(request.getTaskName());
        task.setDescription(request.getDescription());
        task.setTimelineValue(request.getTimelineValue());
        task.setTimelineType(request.getTimelineType());
        task.setStatus(request.getStatus());
        task.setApprovalState(request.getApprovalState());
        task.setApplicableZone(request.getApplicableZone());
        task.setCriticality(request.getCriticality());
        task.setTaskCreatedBy(taskCreatedBy);
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setCompletedDate(request.getCompletedDate());
        task.setCreatedAt(request.getCreatedAt());
        task.setUpdatedAt(request.getUpdatedAt());
        task.setEnable(request.isEnable());
        task.setCompanyId(companyId);
        task.setBusinessUnitId(businessUnitId);
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
        response.setTaskCreatedBy(task.getTaskCreatedBy());
        response.setStartDate(task.getStartDate());
        response.setDueDate(task.getDueDate());
        response.setCompletedDate(task.getCompletedDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setEnable(task.isEnable());
        response.setCompanyId(task.getCompanyId());
        response.setBusinessUnitId(task.getBusinessUnitId());
        response.setBusinessActivityId(task.getBusinessActivityId());
        response.setUserId(task.getUserId());


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
    public ResponseEntity updateTask(ComplianceTaskRequest taskRequest, Long complianceId
                                     ) {
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

    @Override
    public Map<Long, List<TaskResponse>> getCompanyTasks(Long userId) {
        // Fetch user information using Feign Client
        UserResponse userRequestData = authenticationFeignClient.getUserId(userId);
        List<String> roles = userRequestData.getRoles();

        Map<Long, List<TaskResponse>> responseMap = new HashMap<>();

        if (roles == null || !(roles.contains("SUPER_ADMIN") || roles.contains("ADMIN"))) {
            // User is not SUPER_ADMIN or ADMIN, fetch tasks assigned to the user
            List<ComplianceTask> complianceTaskList = complianceTaskRepository.findByAssignedTo(userId);
            List<TaskResponse> taskResponses = getResponseMap(complianceTaskList);
            responseMap.put(userId, taskResponses);
        } else {
            // User is SUPER_ADMIN or ADMIN, fetch all tasks
            List<ComplianceTask> complianceTaskList = complianceTaskRepository.findAll();
            List<TaskResponse> taskResponses = getResponseMap(complianceTaskList);
            responseMap.put(userId, taskResponses);
        }

        return responseMap;
    }

    private List<TaskResponse> getResponseMap(List<ComplianceTask> complianceTaskList) {
        List<TaskResponse> resp = new ArrayList<>();

        for (ComplianceTask complianceTask : complianceTaskList) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setTaskId(complianceTask.getId());
            taskResponse.setTaskName(complianceTask.getTaskName());


            CompanyResponse companyDetails = authenticationFeignClient.getCompanyData(complianceTask.getCompanyId());
            System.out.println(companyDetails);
            taskResponse.setCompanyId(companyDetails.getCompanyId());
            taskResponse.setCompanyName(companyDetails.getCompanyName());

            Optional<Compliance> complianceData = complianceRepo.findById(complianceTask.getCompliance().getId());

            taskResponse.setComplianceId(complianceData.get().getId());
            taskResponse.setComplianceName(complianceData.get().getComplianceName());


            Long businessUnitId = complianceTask.getBusinessUnitId();
            BusinessUnitResponse businessUnitResponse = authenticationFeignClient.getBusinessUnitById(businessUnitId);
            taskResponse.setBusinessAddress(businessUnitResponse.getAddress());
            taskResponse.setBusinessId(businessUnitResponse.getId());
            taskResponse.setTaskDescription(complianceTask.getDescription());
            resp.add(taskResponse);
        }

        return resp;
    }


    public ResponseEntity assignTask(Long assigneeId, List<Long> taskIds, Long assignedBy) {


        UserResponse assigneeResponse = authenticationFeignClient.getUserId(assigneeId);

        if (assigneeResponse == null || assigneeResponse.getUserId() == null) {
            // Assignee not found
            return new ResponseEntity().notFound().ok("Assignee not found with id: " + assigneeId);
        }

        // Retrieve the ComplianceTask entities by a list of task IDs
        List<ComplianceTask> complianceTasks = complianceTaskRepository.findAllByIdIn(taskIds);

        if (complianceTasks.isEmpty()) {
            // No tasks found
            return new ResponseEntity().notFound().build();
        }

        // Update assignee and assignedBy for each task
        for (ComplianceTask task : complianceTasks) {
            task.setAssignedTo(assigneeId);
            task.setAssignedBy(assignedBy);
        }

        // Save the updated tasks
        complianceTaskRepository.saveAll(complianceTasks);

        // You can return the updated tasks or any other response as needed
        return new ResponseEntity().ok("Tasks assigned successfully");
    }


}
