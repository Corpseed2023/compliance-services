package com.lawzoom.complianceservice.controller.documentController;

import com.lawzoom.complianceservice.model.documentModel.File;
import com.lawzoom.complianceservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("userId") Long userId) throws IOException {
        File uploadedFile = fileService.uploadFile(file, userId);
        Map<String, String> response = new HashMap<>();
        response.put("fileName", uploadedFile.getName());
        response.put("url", uploadedFile.getUrl());
        return ResponseEntity.ok(response);
    }
}
