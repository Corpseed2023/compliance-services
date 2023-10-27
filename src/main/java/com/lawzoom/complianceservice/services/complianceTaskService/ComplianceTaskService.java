package com.lawzoom.complianceservice.services.complianceTaskService;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface ComplianceTaskService {
    ResponseEntity findComplianceTaskByCompliance(Long complianceId);

    ResponseEntity saveTask(ComplianceTaskRequest taskRequest, Long complianceId);

    ResponseEntity updateTask(ComplianceTaskRequest taskRequest, Long complianceId);

    ResponseEntity fetchTaskById(Long complianceId, Long taskId);

    ResponseEntity deleteTaskById(Long complianceId, Long taskId);

}
