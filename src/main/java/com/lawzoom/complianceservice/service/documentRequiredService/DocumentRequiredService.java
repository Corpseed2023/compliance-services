package com.lawzoom.complianceservice.service.documentRequiredService;

import java.util.Map;

public interface DocumentRequiredService {
    Map<String, Object> createDocumentRequired(Long milestoneId, String name);

    Map<String, Object> getDocumentsByMilestone(Long milestoneId);
}
