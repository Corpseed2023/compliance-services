package com.lawzoom.complianceservice.controller.complianceTaskController;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceTaskService.ComplianceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/compliance/task/{complianceId}")
@RequestMapping("/compliance/task")

public class ComplianceTaskController {

    @Autowired
    private ComplianceTaskService complianceTaskService;

    @GetMapping
    public ResponseEntity fetchAllTask(@RequestParam("complianceId") Long complianceId){
        return this.complianceTaskService.findComplianceTaskByCompliance(complianceId);
    }

    @PostMapping("/save")
    public ResponseEntity saveTask(@RequestBody ComplianceTaskRequest taskRequest,@RequestParam("complianceId") Long complianceId){
        return this.complianceTaskService.saveTask(taskRequest,complianceId);
    }

    @PutMapping("/update")
    public ResponseEntity updateTask(@RequestBody ComplianceTaskRequest taskRequest,@RequestParam("complianceId") Long complianceId){
        return this.complianceTaskService.updateTask(taskRequest,complianceId);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity fetchTaskById(@RequestParam("complianceId") Long complianceId,@RequestParam("taskId") Long taskId){
        return this.complianceTaskService.fetchTaskById(complianceId,taskId);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity deleteTaskById(@RequestParam("complianceId") Long complianceId,@RequestParam("taskId") Long taskId){
        return this.complianceTaskService.deleteTaskById(complianceId,taskId);
    }
}
