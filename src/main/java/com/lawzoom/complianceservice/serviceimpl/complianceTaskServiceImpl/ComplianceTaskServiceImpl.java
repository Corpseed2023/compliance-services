//package com.lawzoom.complianceservice.serviceImpl.complianceTaskServiceImpl;
//
//import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
//import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
//import com.lawzoom.complianceservice.model.complianceModel.Compliance;
//import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
//import com.lawzoom.complianceservice.repository.ComplianceRepo;
//import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
//import com.lawzoom.complianceservice.response.ResponseEntity;
//import com.lawzoom.complianceservice.service.complianceTaskService.ComplianceTaskService;
//import com.lawzoom.complianceservice.utility.CommonUtil;
//import com.lawzoom.complianceservice.utility.ResponseMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ComplianceTaskServiceImpl implements ComplianceTaskService {
//
//    @Autowired
//    private ComplianceRepo complianceRepo;
//
//    @Autowired
//    private  ComplianceTaskRepository complianceTaskRepository;
//
//    @Autowired
//    private ResponseMapper responseMapper;
//
//    @Override
//    public ResponseEntity<List<ComplianceTaskResponse>> findComplianceTaskByCompliance(Long complianceId) {
//        Optional<Compliance> optionalCompliance = complianceRepo.findById(complianceId);
//
//        if (optionalCompliance.isEmpty()) {
//            return new ResponseEntity().badRequest("Task Not Found !!");
//        }
//
//        List<ComplianceTask> complianceTaskList = complianceTaskRepository.findComplianceTaskByCompliance(optionalCompliance.get());
//        List<ComplianceTaskResponse> responseList = new ArrayList<>();
//
//        for (ComplianceTask t : complianceTaskList) {
//            ComplianceTaskResponse response = new ComplianceTaskResponse();
//            response.setId(t.getId());
//            response.setTaskName(t.getTaskName());
//            response.setTimelineValue(t.getTimelineValue());
//            response.setTimelineType(t.getTimelineType());
//            response.setStatus(t.getStatus());
//            response.setApprovalState(t.getApprovalState());
//            response.setApplicableZone(t.getApplicableZone());
//            response.setEnable(t.isEnable());
//            response.setCreatedAt(t.getCreatedAt());
//            response.setUpdatedAt(t.getUpdatedAt());
//            response.setStartDate(t.getStartDate());
//            response.setDueDate(t.getDueDate());
//            response.setCompletedDate(t.getCompletedDate());
//            response.setAssigneeUserId(t.getAssigneeUserId());
//            response.setReporterUserId(t.getReporterUserId());
//            response.setDescription(t.getDescription());
//            response.setCriticality(t.getCriticality());
//            responseList.add(response);
//        }
//
//        return new ResponseEntity().badRequest("Task Found !!");
//    }
//
//    @Override
//    public ResponseEntity saveTask(ComplianceTaskRequest taskRequest, Long complianceId) {
//        Compliance compliance=this.complianceRepo.findComplianceById(complianceId);
//        if(compliance==null)
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//        ComplianceTask findTask=this.complianceTaskRepository.findTaskByComplianceAndTaskName(compliance,taskRequest.getTaskName());
//        if(findTask!=null)
//            return new ResponseEntity().badRequest("Compliance Task already exist !!");
//
//        ComplianceTask saveTask=this.complianceTaskRepository.save(this.responseMapper.mapToSaveComplianceTask(taskRequest,compliance));
//
//        if(saveTask==null)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }
//
//   /* @Override
//    public ResponseEntity updateTask(ComplianceTaskRequest taskRequest, Long complianceId) {
//        Compliance compliance=this.complianceRepo.findComplianceById(complianceId);
//        if(compliance==null)
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//        ComplianceTask findTask=this.complianceTaskRepository.findTaskByComplianceAndTaskNameAndIdNot(compliance,taskRequest.getTaskName(),taskRequest.getId());
//        if(findTask!=null)
//            return new ResponseEntity().badRequest("Compliance Task already exist !!");
//
//        ComplianceTask updateTask=this.complianceTaskRepository.updateComplianceTask(this.responseMapper.mapToUpdateComplianceTask(taskRequest,compliance));
//        if(updateTask==null)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }*/
//
//    @Override
//    public ResponseEntity<?> updateTask(ComplianceTaskRequest taskRequest, Long complianceId) {
//        Compliance compliance = complianceRepo.findComplianceById(complianceId);
//        if (compliance == null) {
////            return ResponseEntity.badRequest().body("Compliance Not Found !!");
//            return new ResponseEntity().badRequest("Compliance Not Found ");
//
//        }
//
//        ComplianceTask existingTask = complianceTaskRepository.findTaskByComplianceAndTaskNameAndIdNot(
//                compliance, taskRequest.getTaskName(), taskRequest.getId());
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
//        updatedTask.setCompliance(compliance);
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
//
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
//
////    @Override
////    public ResponseEntity deleteTaskById(Long complianceId, Long taskId) {
////        Compliance compliance=this.complianceRepo.findComplianceById(complianceId);
////        if(compliance==null)
////            return new ResponseEntity().badRequest("Compliance Not Found !!");
////
////        ComplianceTask complianceTask=this.complianceTaskRepository.findTaskByComplianceAndId(compliance,taskId);
////        if(complianceTask==null)
////            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
////
////        boolean deleteTask=this.complianceTaskRepository.delete(complianceTask);
////
////        if(!deleteTask)
////            return new ResponseEntity().internalServerError();
////
////        return new ResponseEntity().ok();
////    }
//
//    @Override
//    public ResponseEntity<?> deleteTaskById(Long complianceId, Long taskId) {
//
//        Compliance compliance = this.complianceRepo.findComplianceById(complianceId);
//        if (compliance == null)
////            return ResponseEntity.badRequest().body("Compliance Not Found !!");
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//
//        ComplianceTask complianceTask = this.complianceTaskRepository.findTaskByComplianceAndId(compliance, taskId);
//        if (complianceTask == null)
////            return ResponseEntity.badRequest().body("Compliance Task Not Found !!");
//
//            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
//
//        try {
//
//            this.complianceTaskRepository.delete(complianceTask);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete compliance task");
//            return new ResponseEntity().internalServerError().badRequest("Failed to delete compliance task");
//
//        }
//    }
//
//
//
//    /*public ResponseEntity deleteTaskById(Long complianceId, Long taskId) {
//        Compliance compliance = complianceRepo.findById(complianceId).orElse(null);
//        if (compliance == null)
//            return ResponseEntity.badRequest().body("Compliance Not Found !!");
//
//        ComplianceTask complianceTask = complianceTaskRepository.findById(taskId).orElse(null);
//        if (complianceTask == null || !complianceTask.getCompliance().equals(compliance))
//            return ResponseEntity.badRequest().body("Compliance Task Not Found !!");
//
//        try {
//            complianceTaskRepository.delete(complianceTask);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    } */
//}
