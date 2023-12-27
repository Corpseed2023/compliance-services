//package com.lawzoom.complianceservice.controller.complianceSubTaskController;
//
//import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskRequest;
//import com.lawzoom.complianceservice.response.ResponseEntity;
//import com.lawzoom.complianceservice.services.complianceSubTaskService.ComplianceSubTaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/compliance/sub-task/{taskId}")
//public class ComplianceSubTaskController {
//
//    @Autowired
//    private ComplianceSubTaskService complianceSubTaskService;
//
//    @GetMapping
//    public ResponseEntity fetchAllSubTask(@PathVariable("taskId") Long taskId){
//        return this.complianceSubTaskService.fetchSubTaskByTask(taskId);
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity saveSubTask(@RequestBody ComplianceSubTaskRequest subTaskRequest, @PathVariable("taskId") Long taskId){
//        return this.complianceSubTaskService.saveSubTask(subTaskRequest,taskId);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity updateSubTask(@RequestBody ComplianceSubTaskRequest subTaskRequest,@PathVariable("taskId") Long taskId){
//        return this.complianceSubTaskService.updateSubTask(subTaskRequest,taskId);
//    }
//
//    @GetMapping("/{subTaskId}")
//    public ResponseEntity fetchSubTaskById(@PathVariable("taskId") Long taskId,@PathVariable("subTaskId") Long subTaskId){
//        return this.complianceSubTaskService.fetchSubTaskById(taskId,subTaskId);
//    }
//
//    @DeleteMapping("/{subTaskId}")
//    public ResponseEntity deleteSubTaskById(@PathVariable("taskId") Long taskId,@PathVariable("subTaskId") Long subTaskId){
//        return this.complianceSubTaskService.deleteSubTaskById(taskId,subTaskId);
//    }
//
//}
