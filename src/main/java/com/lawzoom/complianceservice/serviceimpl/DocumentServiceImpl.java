package com.lawzoom.complianceservice.serviceimpl;


import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.repository.DocumentRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;


@Service
public class DocumentServiceImpl implements DocumentService {


    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void saveTaskDocument(Long taskId, MultipartFile file, DocumentRequest documentRequest) {
        // Here you can perform additional validations or business logic if needed

        // Create a Document entity to save in the database
        Document document = new Document();
        document.setDocumentName(documentRequest.getDocumentName());
        document.setFileName(documentRequest.getFileName());
        document.setIssueDate(documentRequest.getIssueDate());
        document.setReferenceNumber(documentRequest.getReferenceNumber());
        document.setRemarks(documentRequest.getRemarks());
        document.setUploadDate(new Date()); // Assuming you want to set the upload date to the current date
        // Set other properties based on your needs

        // Set the association with the task (assuming a ManyToOne relationship with ComplianceTask)
        // You may need to modify this based on your actual data model
        // For simplicity, assuming that each document is associated with a task using taskId
        ComplianceTask task = new ComplianceTask();
        task.setId(taskId);
        document.setComplianceTask(task);

        // Save the document in the database
        documentRepository.save(document);

        // Handle file upload - Save the file to a specific location (D:\?uploadedFile)
        if (file != null && !file.isEmpty()) {
            try {
                // Create the directory if it doesn't exist

                Files.createDirectories(Paths.get("D:/uploadedFile"));

                // Save the file to the specified location
                Path filePath = Paths.get("D:/uploadedFile", document.getFileName());

                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                // Handle exceptions related to file processing
                e.printStackTrace();
            }
        }

    }
    @Override
    public ResponseEntity updateTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity fetchTaskDocument(Long id, Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity deleteTaskDocument(Long id, Long taskId) {
        return null;
    }

    @Override
    public ResponseEntity fetchAllTaskDocument(Long taskId) {
        return null;
    }


}
