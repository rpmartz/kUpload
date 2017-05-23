package com.ryanpmartz.kupload.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UploadService {

    private static final Logger log = Logger.getLogger(UploadService.class);

    private TransferManager transferManager;
    private String s3Bucket;

    @Autowired
    public UploadService(TransferManager transferManager, @Value("s3.bucket.name") String s3Bucket) {
        this.transferManager = transferManager;
        this.s3Bucket = s3Bucket;
    }

    public void uploadFile(MultipartFile multipartFile) {
        String extension = determineExtension(multipartFile);
        String filename = UUID.randomUUID().toString() + extension;

        try (InputStream is = multipartFile.getInputStream()) {

            PutObjectRequest req = new PutObjectRequest(s3Bucket, filename, is, new ObjectMetadata());
            req.withCannedAcl(CannedAccessControlList.Private);

            transferManager.upload(req);

        } catch (IOException e) {
            log.error("IOException occurred uploading file", e);
            throw new RuntimeException(e);
        }
    }

    private String determineExtension(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();

        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
