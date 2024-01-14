package com.lawzoom.complianceservice.serviceimpl.complianceTaskServiceImpl;

import com.lawzoom.complianceservice.dto.TaskResponse;
import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
import com.lawzoom.complianceservice.feignClient.CompanyFeignClient;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
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
    private CompanyFeignClient companyFeignClient;

    @Autowired
    private ComplianceService complianceService;



    @Override
    public ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId,
                                           Long companyId, Long businessUnitId) {

        if (complianceId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compliance ID cannot be null");
        }
        Optional<Compliance> complianceData = complianceRepo.findById(complianceId);

        ComplianceTask savedTask = complianceData.map(compliance ->
                        complianceTaskRepository.save(mapRequestToEntity(taskRequest, compliance,companyId,businessUnitId)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance not found"));

        return mapEntityToResponse(savedTask);
    }

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
        task.setCompliance(compliance);
        task.setUserId(request.getUserId());

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
        response.setUserId(task.getUserId());

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

//    @Override
//    public List<Map<String, List<String>>> getAssigneeAllTasks(Long userId) {
//        List<Map<String, List<String>>> taskData = new ArrayList<>();
//
//        List<ComplianceTask> complianceTasks = complianceTaskRepository.findByUserId(userId);
//
//        for (ComplianceTask complianceTaskData : complianceTasks) {
//            System.out.println(complianceTaskData.getTaskName());
//
//            Long companyId = complianceTaskData.getCompanyId();
//
//            CompanyResponse companyDetails = companyFeignClient.getCompanyData(companyId);
//
//            Long businessUnitId = complianceTaskData.getBusinessUnitId();
//
//
//            BusinessUnitResponse businessUnitResponseDetails = companyFeignClient.getBusinessUnitDetails(businessUnitId);
//
//            // Construct your task data map here
//            Map<String, List<String>> taskMap = new HashMap<>();
//            taskMap.put("taskName", Collections.singletonList(complianceTaskData.getTaskName()));
//            // Add more task attributes as needed
//
//            taskData.add(taskMap);
//        }
//
//        return taskData;
//    }

    @Override
    public Map<Long, List<TaskResponse>> getCompanyTasks(Long userId) {
        List<Map<String, Object>> companyTaskData = new ArrayList<>();
        List<TaskResponse> taskResponseList = new ArrayList<>();

        Map<Long,List<TaskResponse>> responseMap = new HashMap<>();


        List<ComplianceTask> complianceTaskList = complianceTaskRepository.findByUserId(userId);

        List<TaskResponse> taskResponses = getResponseMap(complianceTaskList);
        responseMap.put(userId,taskResponses);
        return responseMap;

        // Check if complianceTaskList is not empty before proceeding
        /*if (!complianceTaskList.isEmpty()) {
            // Fetch company details using Feign client
            CompanyResponse companyDetails = companyFeignClient.getCompanyData(complianceTaskList.get(0).getCompanyId());

            // Check if companyDetails is not null before proceeding
            if (companyDetails != null) {
                Long companyId = companyDetails.getCompanyId();

                // Fetch business units for the company using Feign client
                List<BusinessUnitResponse> businessUnits = companyFeignClient.getAllBusinessUnits(companyId);

                // Check if businessUnits is not empty before proceeding
                if (!businessUnits.isEmpty()) {
                    for (BusinessUnitResponse businessUnit : businessUnits) {
                        TaskResponse taskResponse = new TaskResponse();

                        // Fetch compliances for each business unit using Feign client
                        List<Compliance> compliances = complianceService.getCompliancesByBusinessUnitId(businessUnit.getId());
                        taskResponse.setCompanyName(companyDetails.getCompanyName());
                        taskResponse.setBusinessAddress(businessUnit.getAddress());
//                        taskResponse.setTaskName(task.getTaskName());
                        // Check if compliances is not null before proceeding
                        if (compliances != null) {
                            for (Compliance compliance : compliances) {
                                // Fetch tasks for each compliance from the repository
                                List<ComplianceTask> tasks = complianceTaskRepository.findByComplianceId(compliance.getId());
                                // Check if tasks is not empty before proceeding
                                if (!tasks.isEmpty()) {
                                    for (ComplianceTask task : tasks) {
                                        taskResponse.setCompanyName(companyDetails.getCompanyName());
                                        taskResponse.setBusinessAddress(businessUnit.getAddress());
                                        taskResponse.setTaskName(task.getTaskName());

                                        // Construct your task data map here
                                       *//* Map<String, Object> taskMap = new HashMap<>();
                                        taskMap.put("companyName", companyDetails.getCompanyName());
                                        taskMap.put("businessUnit", businessUnit.getAddress());
                                        taskMap.put("complianceName", compliance.getName());
                                        taskMap.put("taskId", task.getId());
                                        taskMap.put("taskName", task.getTaskName());
                                        taskMap.put("description", task.getDescription());


                                        taskMap.put("companyName", companyDetails.getCompanyName());
                                        taskMap.put("businessUnit", businessUnit.getAddress());
                                        taskMap.put("complianceName", compliance.getName());
                                        taskMap.put("taskId", task.getId());
                                        taskMap.put("taskName", task.getTaskName());
                                        taskMap.put("description", task.getDescription());*//*



                                        // Add more task attributes as needed

                                       // companyTaskData.add(taskMap);

                                    }
                                }
                            }
                        }
                        taskResponseList.add(taskResponse);

                    }
                }
            }
            responseMap.put(userId,taskResponseList);
        }

        // Log the final result
        System.out.println("companyTaskData: " + companyTaskData);

        return responseMap;*/
    }

    private List<TaskResponse> getResponseMap(List<ComplianceTask> complianceTaskList) {
        List<TaskResponse> resp = new ArrayList<>();

        for(ComplianceTask complianceTask : complianceTaskList){
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setTaskName(complianceTask.getTaskName());
            CompanyResponse companyDetails = companyFeignClient.getCompanyData(complianceTask.getCompanyId());
            taskResponse.setCompanyName(companyDetails.getCompanyName());
            Long businessUnitId = complianceTask.getBusinessUnitId();
            BusinessUnitResponse businessUnitResponse = companyFeignClient.getBusinessUnitById(businessUnitId);
            taskResponse.setBusinessAddress(businessUnitResponse.getAddress());
            resp.add(taskResponse);
        }
        return resp;
    }


}
