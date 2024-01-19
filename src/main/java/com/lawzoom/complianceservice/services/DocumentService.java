package com.lawzoom.complianceservice.services;


import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {


    void saveTaskDocument(Long taskId, MultipartFile file, DocumentRequest documentRequest);
}
