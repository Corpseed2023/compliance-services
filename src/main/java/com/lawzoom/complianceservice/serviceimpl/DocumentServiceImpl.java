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
import java.util.Optional;


@Service
public class DocumentServiceImpl implements DocumentService {


    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void saveTaskDocument(Long taskId, MultipartFile file){

//        Document document = new Document();
//        document.setDocumentName(documentRequest.getDocumentName());
//        document.setFileName(documentRequest.getFileName());
//        document.setIssueDate(documentRequest.getIssueDate());
//        document.setReferenceNumber(documentRequest.getReferenceNumber());
//        document.setRemarks(documentRequest.getRemarks());
//        document.setUploadDate(new Date()); // Assuming you want to set the upload date to the current date

//        ComplianceTask task = new ComplianceTask();
//        task.setId(taskId);
//        document.setComplianceTask(task);
//
//        documentRepository.save(document);

        if (file != null && !file.isEmpty()) {
            try {

                Files.createDirectories(Paths.get("D:/uploadedFile"));

                Path filePath = Paths.get("D:/uploadedFile");

                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                // Handle exceptions related to file processing
                e.printStackTrace();
            }
        }

    }
//    @Override
//    public ResponseEntity updateTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId) {
//        return null;
//    }

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
