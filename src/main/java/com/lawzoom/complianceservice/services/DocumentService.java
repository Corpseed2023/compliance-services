package com.lawzoom.complianceservice.services;


import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface DocumentService {




    void saveTaskDocument(Long taskId, MultipartFile file,DocumentRequest documentRequest);

    ResponseEntity updateTaskDocument(Optional<MultipartFile> file, Long taskId);


    ResponseEntity fetchTaskDocument(Long id, Long taskId);

    ResponseEntity deleteTaskDocument(Long id, Long taskId);

    ResponseEntity fetchAllTaskDocument(Long taskId);

}
