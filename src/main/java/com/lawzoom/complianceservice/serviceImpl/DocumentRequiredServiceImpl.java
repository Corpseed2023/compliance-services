package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.documentRequiredDto.CreateDocumentResponseDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.DocumentRequiredDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.MilestoneDocumentsResponseDTO;
import com.lawzoom.complianceservice.model.mileStoneModel.DocumentRequired;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.repository.DocumentRequiredRepository;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
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
    private MilestoneRepository milestoneRepository;

    /**
     * Create a new document for a milestone
     *
     * @param milestoneId ID of the milestone
     * @param name        Name of the document
     * @return CreateDocumentResponseDTO
     */
    @Override
    public CreateDocumentResponseDTO createDocumentRequired(Long milestoneId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Document name cannot be null or empty.");
        }

        // Validate milestone existence
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + milestoneId));

        // Create and save the document
        DocumentRequired documentRequired = new DocumentRequired();
        documentRequired.setName(name.trim());
        documentRequired.setMilestone(milestone);
        documentRequired = documentRequiredRepository.save(documentRequired);

        // Convert saved document to DTO and return response
        DocumentRequiredDTO documentRequiredDTO = convertToDTO(documentRequired);
        return new CreateDocumentResponseDTO("success", "Document created successfully.", documentRequiredDTO);
    }

    /**
     * Get all documents for a specific milestone
     *
     * @param milestoneId ID of the milestone
     * @return MilestoneDocumentsResponseDTO
     */
    @Override
    public MilestoneDocumentsResponseDTO getDocumentsByMilestone(Long milestoneId) {
        // Validate milestone existence
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + milestoneId));

        // Fetch all documents associated with the milestone
        List<DocumentRequired> documents = documentRequiredRepository.findByMilestoneId(milestoneId);

        // Convert document entities to DTOs
        List<DocumentRequiredDTO> documentDTOs = documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Prepare and return response
        return new MilestoneDocumentsResponseDTO(milestone.getId(), documentDTOs);
    }

    /**
     * Utility method to convert a DocumentRequired entity to DocumentRequiredDTO
     *
     * @param documentRequired The DocumentRequired entity
     * @return DocumentRequiredDTO
     */
    private DocumentRequiredDTO convertToDTO(DocumentRequired documentRequired) {
        return new DocumentRequiredDTO(
                documentRequired.getId(),
                documentRequired.getName(),
                documentRequired.getMilestone().getId()
        );
    }
}
