package com.lawzoom.complianceservice.services.complianceTaskService;

import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;

import java.util.List;

public interface ComplianceTaskService {



    ResponseEntity updateTask(ComplianceTaskRequest taskRequest, Long complianceId);

    ResponseEntity fetchTaskById(Long complianceId, Long taskId);

    ResponseEntity deleteTaskById(Long complianceId, Long taskId);



    ComplianceTaskResponse saveTask(ComplianceTaskRequest taskRequest, Long complianceId, Long companyId, Long businessUnitId);

    List<ComplianceTaskResponse> getAllTaskByComplianceId(Long complianceId);


}
