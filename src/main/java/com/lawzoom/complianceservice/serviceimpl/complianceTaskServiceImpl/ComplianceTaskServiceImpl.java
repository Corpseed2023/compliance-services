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

@Service
public class ComplianceTaskServiceImpl implements ComplianceTaskService {

    @Autowired
    private ComplianceRepo complianceRepo;

    @Autowired
    private  ComplianceTaskRepository complianceTaskRepository;



//    @Override
//    public ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId,
//                                           Long companyId, Long businessUnitId) {
//
//        if (complianceId == null) {
//            // Handle the case where complianceId is null
//            // Return a 400 Bad Request response
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compliance ID cannot be null");
//        }
//
//        Optional<Compliance> complianceData = complianceRepo.findById(complianceId);
//
//        if (complianceData.isPresent()) {
//            ComplianceTask newTask = mapRequestToEntity(taskRequest, complianceData.get());
//
//            ComplianceTask savedTask = complianceTaskRepository.save(newTask);
//
//            if (savedTask != null && savedTask.getId() != null) {
//                return mapEntityToResponse(savedTask);
//            } else {
//                // Handle the case where the task data is not saved
//                // Return a 500 Internal Server Error response
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Task data not saved");
//            }
//        } else {
//            // Handle the case where the compliance data is not found
//            // Return a 404 error response
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance not found");
//        }
//    }

    @Override
    public ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId,
                                           Long companyId, Long businessUnitId) {
        validateRequestParameters(complianceId);

        Optional<Compliance> complianceData = complianceRepo.findById(complianceId);

        ComplianceTask savedTask = complianceData.map(compliance ->
                        complianceTaskRepository.save(mapRequestToEntity(taskRequest, compliance)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance not found"));

        return mapEntityToResponse(savedTask);
    }

    private void validateRequestParameters(Long complianceId) {
        if (complianceId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compliance ID cannot be null");
        }
    }

    // Helper method to map request DTO to entity
    private ComplianceTask mapRequestToEntity(ComplianceTaskRequest request, Compliance compliance) {
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
        task.setCompanyId(request.getCompanyId());
        task.setBusinessUnitId(request.getBusinessUnitId());
        task.setBusinessActivityId(request.getBusinessActivityId());
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
    public ResponseEntity<List<ComplianceTaskResponse>> findComplianceTaskByCompliance(Long complianceId) {
        Optional<Compliance> optionalCompliance = complianceRepo.findById(complianceId);

        if (optionalCompliance.isEmpty()) {
            return new ResponseEntity().badRequest("Task Not Found !!");
        }

        List<ComplianceTask> complianceTaskList = complianceTaskRepository.findComplianceTaskByCompliance(optionalCompliance.get());
        List<ComplianceTaskResponse> responseList = new ArrayList<>();

        for (ComplianceTask t : complianceTaskList) {
            ComplianceTaskResponse response = new ComplianceTaskResponse();
            response.setId(t.getId());
            response.setTaskName(t.getTaskName());
            response.setTimelineValue(t.getTimelineValue());
            response.setTimelineType(t.getTimelineType());
            response.setStatus(t.getStatus());
            response.setApprovalState(t.getApprovalState());
            response.setApplicableZone(t.getApplicableZone());
            response.setEnable(t.isEnable());
            response.setCreatedAt(t.getCreatedAt());
            response.setUpdatedAt(t.getUpdatedAt());
            response.setStartDate(t.getStartDate());
            response.setDueDate(t.getDueDate());
            response.setCompletedDate(t.getCompletedDate());
            response.setAssigneeUserId(t.getAssigneeUserId());
            response.setReporterUserId(t.getReporterUserId());
            response.setDescription(t.getDescription());
            response.setCriticality(t.getCriticality());
            responseList.add(response);
        }

        return new ResponseEntity().badRequest("Task Found !!");
    }

//    @Override
//    public ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId) {
//        Compliance compliance = this.complianceRepo.findComplianceById(complianceId);
//        if (compliance == null)
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//        ComplianceTask findTask = this.complianceTaskRepository.findTaskByComplianceAndTaskName(compliance, taskRequest.getTaskName());
//        if (findTask != null)
//            return new ResponseEntity().badRequest("Compliance Task already exists !!");
//
//        ComplianceTask saveTask = new ComplianceTask();
//        saveTask.setTaskName(taskRequest.getTaskName());
//        saveTask.setTimelineValue(taskRequest.getTimelineValue());
//        saveTask.setTimelineType(taskRequest.getTimelineType());
//        saveTask.setStatus(taskRequest.getStatus());
//        saveTask.setApprovalState(taskRequest.getApprovalState());
//        saveTask.setApplicableZone(taskRequest.getApplicableZone());
//        saveTask.setEnable(true);
//        saveTask.setCreatedAt(CommonUtil.getDate());
//        saveTask.setStartDate(taskRequest.getStartDate());
//        saveTask.setDueDate(taskRequest.getDueDate());
//        saveTask.setCompletedDate(taskRequest.getCompletedDate());
//        saveTask.setReporterUserId(taskRequest.getReporterUserId());
//        saveTask.setAssigneeUserId(taskRequest.getAssigneeUserId());
//        saveTask.setCriticality(taskRequest.getCriticality());
//        saveTask.setDescription(taskRequest.getDescription());
//        saveTask.setUpdatedAt(CommonUtil.getDate());
//        saveTask.setCompliance(compliance);
//
//        ComplianceTask savedTask = this.complianceTaskRepository.save(saveTask);
//
//        if (savedTask == null)
//            return new ResponseEntity().internalServerError();
//
//        return ResponseEntity.ok().build();
//    }

//    @Override
//    public ResponseEntity<?> updateTask(ComplianceTaskRequest taskRequest, Long complianceId) {
//        Optional<Compliance> compliance = complianceRepo.findById(complianceId);
//        if (compliance == null) {
////            return ResponseEntity.badRequest().body("Compliance Not Found !!");
//            return new ResponseEntity().badRequest("Compliance Not Found ");
//
//        }
//
//        ComplianceTask existingTask = complianceTaskRepository.findTaskByComplianceAndTaskNameAndIdNot(
//                compliance.get(), taskRequest.getTaskName(), taskRequest.getId());
//
//        if (existingTask != null) {
////            return ResponseEntity.badRequest().body("Compliance Task already exists !!");
//            return new ResponseEntity().badRequest("Compliance Task already exists");
//
//        }
//
//        ComplianceTask updatedTask = new ComplianceTask();
//        updatedTask.setId(taskRequest.getId());
//        updatedTask.setTaskName(taskRequest.getTaskName());
//        updatedTask.setTimelineValue(taskRequest.getTimelineValue());
//        updatedTask.setTimelineType(taskRequest.getTimelineType());
//        updatedTask.setStatus(taskRequest.getStatus());
//        updatedTask.setApprovalState(taskRequest.getApprovalState());
//        updatedTask.setApplicableZone(taskRequest.getApplicableZone());
//        updatedTask.setEnable(taskRequest.isEnable());
//        updatedTask.setCreatedAt(taskRequest.getCreatedAt());
//        updatedTask.setStartDate(taskRequest.getStartDate());
//        updatedTask.setDueDate(taskRequest.getDueDate());
//        updatedTask.setCompletedDate(taskRequest.getCompletedDate());
//        updatedTask.setReporterUserId(taskRequest.getReporterUserId());
//        updatedTask.setAssigneeUserId(taskRequest.getAssigneeUserId());
//        updatedTask.setCriticality(taskRequest.getCriticality());
//        updatedTask.setUpdatedAt(CommonUtil.getDate());
//        updatedTask.setDescription(taskRequest.getDescription());
//        updatedTask.setCompliance(compliance.get());
//
//        updatedTask = complianceTaskRepository.save(updatedTask);
//
//        if (updatedTask == null) {
//            return new ResponseEntity().badRequest("Failed to update compliance task");
//        }
//
//        return ResponseEntity.ok().build();
//    }


//
//    @Override
//    public ResponseEntity fetchTaskById(Long complianceId, Long taskId) {
//        Compliance compliance=this.complianceTaskRepository.findComplianceById(complianceId);
//        if(compliance==null)
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//        ComplianceTask complianceTask=this.complianceTaskRepository.findTaskByComplianceAndId(compliance,taskId);
//        if(complianceTask==null)
//            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
//
//        return new ResponseEntity().ok(this.responseMapper.mapToComplianceTaskResponse(complianceTask));
//    }

//    @Override
//    public ResponseEntity deleteTaskById(Long complianceId, Long taskId) {
//        Compliance compliance=this.complianceRepo.findComplianceById(complianceId);
//        if(compliance==null)
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//        ComplianceTask complianceTask=this.complianceTaskRepository.findTaskByComplianceAndId(compliance,taskId);
//        if(complianceTask==null)
//            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
//
//        boolean deleteTask=this.complianceTaskRepository.delete(complianceTask);
//
//        if(!deleteTask)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }

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

    /*public ResponseEntity deleteTaskById(Long complianceId, Long taskId) {
        Compliance compliance = complianceRepo.findById(complianceId).orElse(null);
        if (compliance == null)
            return ResponseEntity.badRequest().body("Compliance Not Found !!");

        ComplianceTask complianceTask = complianceTaskRepository.findById(taskId).orElse(null);
        if (complianceTask == null || !complianceTask.getCompliance().equals(compliance))
            return ResponseEntity.badRequest().body("Compliance Task Not Found !!");

        try {
            complianceTaskRepository.delete(complianceTask);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    } */

    @Override
    public ResponseEntity getAllTasksByComplianceId(Long complianceId) {
        return null;
    }


}
