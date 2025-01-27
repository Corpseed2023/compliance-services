package com.lawzoom.complianceservice.controller.milestoneController;

import com.lawzoom.complianceservice.dto.documentRequiredDto.CreateDocumentResponseDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.ComplianceDocumentsResponseDTO;
import com.lawzoom.complianceservice.service.documentRequiredService.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance/documents")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DocumentRequiredController {

    @Autowired
    private DocumentRequiredService documentRequiredService;

    /**
     * Create a new DocumentRequired for a compliance.
     *
     * @param complianceId ID of the compliance
     * @param name         Name of the document
     * @return ResponseEntity with a success message and document details
     */
    @PostMapping("/create")
    public ResponseEntity<CreateDocumentResponseDTO> createDocument(
            @RequestParam Long complianceId,
            @RequestParam String name) {
        CreateDocumentResponseDTO response = documentRequiredService.createDocumentRequired(complianceId, name);
        return ResponseEntity.ok(response);
    }

    /**
     * Fetch all documents required for a specific compliance.
     *
     * @param complianceId ID of the compliance
     * @return ResponseEntity with compliance details and a list of documents
     */
    @GetMapping("/compliance")
    public ResponseEntity<ComplianceDocumentsResponseDTO> getDocumentsByCompliance(
            @RequestParam Long complianceId) {
        ComplianceDocumentsResponseDTO response = documentRequiredService.getDocumentsByCompliance(complianceId);
        return ResponseEntity.ok(response);
    }
}
