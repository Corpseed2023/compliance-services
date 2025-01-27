package com.lawzoom.complianceservice.controller.milestoneController;

import com.lawzoom.complianceservice.service.documentRequiredService.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/compliance/documents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentRequiredController {

    @Autowired
    private DocumentRequiredService documentRequiredService;

    /**
     * Create a new DocumentRequired for a milestone.
     *
     * @param milestoneId ID of the milestone
     * @param name        Name of the document
     * @return ResponseEntity with a success message and document details
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createDocument(@RequestParam Long milestoneId,
                                                              @RequestParam String name) {
        Map<String, Object> response = documentRequiredService.createDocumentRequired(milestoneId, name);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetch all documents required for a specific milestone.
     *
     * @param milestoneId ID of the milestone
     * @return ResponseEntity with milestone details and a list of documents
     */
    @GetMapping("/milestone")
    public ResponseEntity<Map<String, Object>> getDocumentsByMilestone(@RequestParam Long milestoneId) {
        Map<String, Object> response = documentRequiredService.getDocumentsByMilestone(milestoneId);
        return ResponseEntity.ok(response);
    }
}
