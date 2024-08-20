package com.Boujorno.adnanemanager.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class FileStorageUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageUtil.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Created directory: " + uploadPath);
            }

            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("File saved: " + filePath);
            return filePath.toString();
        } catch (IOException e) {
            logger.error("Failed to save file", e);
            return null;
        }
    }
}
