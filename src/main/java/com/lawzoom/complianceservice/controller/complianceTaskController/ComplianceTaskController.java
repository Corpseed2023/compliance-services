package com.lawzoom.complianceservice.controller.complianceTaskController;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceTaskService.ComplianceTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/compliance/task/{complianceId}")
@RequestMapping("/compliance/task")

public class ComplianceTaskController {

    @Autowired
    private ComplianceTaskService complianceTaskService;

    @PostMapping("/saveYourTask")
    public ComplianceTaskResponse saveTask(@RequestBody ComplianceTaskRequest taskRequest,
                                           @RequestParam("complianceId") Long complianceId,
                                           @RequestParam("company_Id") Long companyId,
                                           @RequestParam("businessUnit_ID") Long businessUnitId)

    {
        return this.complianceTaskService.saveTask(taskRequest,complianceId,companyId,businessUnitId);
    }


    @GetMapping("/getAllComplianceTask")
    public List<ComplianceTaskResponse> getAllTaskByCompliance(@RequestParam("complianceId") Long complianceId) {
        return this.complianceTaskService.getAllTaskByComplianceId(complianceId);
    }


    @GetMapping("/getCompanyTasks")
    public List<Map<String, Object>> getCompanyTasks(@RequestParam("userId") Long userId) {
        return complianceTaskService.getCompanyTasks(userId);
    }

    @PutMapping("/update")
    public ResponseEntity updateTask(@RequestBody ComplianceTaskRequest taskRequest,@RequestParam("complianceId") Long complianceId){
        return this.complianceTaskService.updateTask(taskRequest,complianceId);
    }

    @GetMapping("/taskId")
    public ResponseEntity fetchTaskById(@RequestParam("complianceId") Long complianceId,@RequestParam("taskId") Long taskId){
        return this.complianceTaskService.fetchTaskById(complianceId,taskId);
    }

    @DeleteMapping("/taskId")
    public ResponseEntity deleteTaskById(@RequestParam("complianceId") Long complianceId,@RequestParam("taskId") Long taskId){
        return this.complianceTaskService.deleteTaskById(complianceId,taskId);
    }
}
