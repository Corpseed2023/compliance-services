package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.model.documentModel.File;
import com.lawzoom.complianceservice.repository.FileRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3Client s3Client;

//    @Override
//    public File uploadFile(MultipartFile multipartFile, Long userId) throws IOException {
//        String originalFilename = multipartFile.getOriginalFilename();
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;
//
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(uniqueFileName)
//                .contentType(multipartFile.getContentType())
//                .build();
//
//        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
//
//        // Construct the file URL (adjust based on your S3 bucket's URL pattern)
//        String fileUrl = String.format("https://%s.s3.amazonaws.com/%s", bucketName, uniqueFileName);
//
//        File file = new File();
//        file.setName(originalFilename);
//        file.setType(multipartFile.getContentType());
//        file.setUrl(fileUrl);
//        file.setUploadedBy(userId);
//        file.setUploadTimestamp(LocalDateTime.now());
//
//        return fileRepository.save(file);
//    }

    @Override
    public File uploadFile(MultipartFile multipartFile, Long userId) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        // Temporary placeholder URL
        String fileUrl = "https://placeholder.url/" + uniqueFileName;

        File file = new File();
        file.setName(originalFilename);
        file.setType(multipartFile.getContentType());
        file.setUrl(fileUrl);
        file.setUploadedBy(userId);
        file.setUploadTimestamp(LocalDateTime.now());

        return fileRepository.save(file);
    }

}
