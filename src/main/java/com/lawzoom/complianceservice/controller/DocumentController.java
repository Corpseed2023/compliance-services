package com.lawzoom.complianceservice.controller;

import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/uploadDocument")
    public ResponseEntity<String> uploadDocument(@PathVariable Long taskId,@RequestParam("file") MultipartFile file,
            @RequestParam DocumentRequest documentRequest
    )

    {

        documentService.saveTaskDocument(taskId, file, documentRequest);
        return ResponseEntity.ok("Document uploaded successfully.");
    }

}
