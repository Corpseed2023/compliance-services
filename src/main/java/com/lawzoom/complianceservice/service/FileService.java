package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.model.documentModel.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    File uploadFile(MultipartFile file, Long userId) throws IOException;
}
