package com.lawzoom.complianceservice.serviceimpl;


import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.services.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class DocumentServiceImpl implements DocumentService {


    @Override
    public void saveTaskDocument(Long taskId, MultipartFile file, DocumentRequest documentRequest) {

    }
}
