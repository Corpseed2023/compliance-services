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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;



@Service
public class DocumentServiceImpl implements DocumentService {


    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void saveTaskDocument(Long taskId, MultipartFile file, DocumentRequest documentRequest) {

        Document document = new Document();
        document.setDocumentName(documentRequest.getDocumentName());
        document.setFileName(documentRequest.getFileName());
        document.setIssueDate(documentRequest.getIssueDate());
        document.setReferenceNumber(documentRequest.getReferenceNumber());
        document.setRemarks(documentRequest.getRemarks());
        document.setUploadDate(new Date()); // Assuming you want to set the upload date to the current date

        ComplianceTask task = new ComplianceTask();
        task.setId(taskId);
        document.setComplianceTask(task);

        documentRepository.save(document);
        if (file != null && !file.isEmpty()) {
            try {
                String directoryPath = "D:/uploadedFile";
                Files.createDirectories(Paths.get(directoryPath));

                // Format the date using SimpleDateFormat to include in the filename
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String formattedDate = dateFormat.format(new Date());

                // Customize the filename using taskId, upload date, and original filename
                String fileName = taskId + "_" + formattedDate + "_" + file.getOriginalFilename();

                Path filePath = Paths.get(directoryPath, fileName);

                Files.write(filePath, file.getBytes());

                // Now, you can save the file details or perform any other operations as needed
                // For example, you can save the file information in the database

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
    public ResponseEntity updateTaskDocument(Optional<MultipartFile> file, Long taskId) {
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
