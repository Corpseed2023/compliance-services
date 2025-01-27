package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.documentRequiredDto.ComplianceDocumentsResponseDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.CreateDocumentResponseDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.DocumentRequiredDTO;
import com.lawzoom.complianceservice.model.mileStoneModel.DocumentRequired;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.repository.DocumentRequiredRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.service.documentRequiredService.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentRequiredServiceImpl implements DocumentRequiredService {

    @Autowired
    private DocumentRequiredRepository documentRequiredRepository;

    @Autowired
    private ComplianceRepo complianceRepo;

    @Override
    public CreateDocumentResponseDTO createDocumentRequired(Long complianceId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Document name cannot be null or empty.");
        }

        // Validate compliance existence
        Compliance compliance = complianceRepo.findById(complianceId)
                .orElseThrow(() -> new IllegalArgumentException("Compliance not found with ID: " + complianceId));

        // Create and save the document
        DocumentRequired documentRequired = new DocumentRequired();
        documentRequired.setName(name.trim());
        documentRequired.setCompliance(compliance);
        documentRequired = documentRequiredRepository.save(documentRequired);

        // Convert to DTO and return
        DocumentRequiredDTO documentRequiredDTO = convertToDTO(documentRequired);
        return new CreateDocumentResponseDTO("success", "Document created successfully.", documentRequiredDTO);
    }

    @Override
    public ComplianceDocumentsResponseDTO getDocumentsByCompliance(Long complianceId) {
        // Validate compliance existence
        Compliance compliance = complianceRepo.findById(complianceId)
                .orElseThrow(() -> new IllegalArgumentException("Compliance not found with ID: " + complianceId));

        // Fetch all documents associated with the compliance
        List<DocumentRequired> documents = documentRequiredRepository.findByComplianceId(complianceId);

        // Convert document entities to DTOs
        List<DocumentRequiredDTO> documentDTOs = documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Prepare and return response
        return new ComplianceDocumentsResponseDTO(compliance.getId(), documentDTOs);
    }

    private DocumentRequiredDTO convertToDTO(DocumentRequired documentRequired) {
        return new DocumentRequiredDTO(
                documentRequired.getId(),
                documentRequired.getName(),
                documentRequired.getCompliance().getId()
        );
    }
}
