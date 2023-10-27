package com.lawzoom.complianceservice.controller.complianceTaskController;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceTaskService.ComplianceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/task/{complianceId}")
public class ComplianceTaskController {

    @Autowired
    private ComplianceTaskService complianceTaskService;

    @GetMapping
    public ResponseEntity fetchAllTask(@PathVariable("complianceId") Long complianceId){
        return this.complianceTaskService.findComplianceTaskByCompliance(complianceId);
    }

    @PostMapping("/save")
    public ResponseEntity saveTask(@RequestBody ComplianceTaskRequest taskRequest,@PathVariable("complianceId") Long complianceId){
        return this.complianceTaskService.saveTask(taskRequest,complianceId);
    }

    @PutMapping("/update")
    public ResponseEntity updateTask(@RequestBody ComplianceTaskRequest taskRequest,@PathVariable("complianceId") Long complianceId){
        return this.complianceTaskService.updateTask(taskRequest,complianceId);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity fetchTaskById(@PathVariable("complianceId") Long complianceId,@PathVariable("taskId") Long taskId){
        return this.complianceTaskService.fetchTaskById(complianceId,taskId);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity deleteTaskById(@PathVariable("complianceId") Long complianceId,@PathVariable("taskId") Long taskId){
        return this.complianceTaskService.deleteTaskById(complianceId,taskId);
    }
}
