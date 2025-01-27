package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.model.mileStoneModel.DocumentRequired;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.repository.DocumentRequiredRepository;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.service.documentRequiredService.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentRequiredServiceImpl implements DocumentRequiredService {

    @Autowired
    private DocumentRequiredRepository documentRequiredRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    public Map<String, Object> createDocumentRequired(Long milestoneId, String name) {
        // Validate milestone existence
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + milestoneId));

        // Create and save the document
        DocumentRequired documentRequired = new DocumentRequired();
        documentRequired.setName(name);
        documentRequired.setMilestone(milestone);
        documentRequiredRepository.save(documentRequired);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Document created successfully.");
        response.put("document", documentRequired);

        return response;
    }

    @Override
    public Map<String, Object> getDocumentsByMilestone(Long milestoneId) {
        // Validate milestone existence
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + milestoneId));

        // Fetch documents for the milestone
        List<DocumentRequired> documents = documentRequiredRepository.findByMilestone(milestone);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("milestoneId", milestoneId);
        response.put("documents", documents);
        response.put("totalDocuments", documents.size());

        return response;
    }
}
