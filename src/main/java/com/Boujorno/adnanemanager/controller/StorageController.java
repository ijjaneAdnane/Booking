package com.Boujorno.adnanemanager.controller;

import com.Boujorno.adnanemanager.service.impl.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/fileSystem")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    //@GetMapping("/fileSystem/{fileName}")
    //public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
    //  byte[] imageData = service.downloadImageFromFileSystem(fileName);
    //return ResponseEntity.status(HttpStatus.OK)
    //      .contentType(MediaType.valueOf("image/png"))
    //    .body(imageData);
    //}
    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("C:/Users/Dell/Desktop/adnanemanager/src/main/resources/uploads/" + fileName);
            System.out.println("Fetching file: " + filePath.toString()); // Log pour vérifier le chemin

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found");
            }

            byte[] imageData = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(contentType))
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the file");
        }
    }
}