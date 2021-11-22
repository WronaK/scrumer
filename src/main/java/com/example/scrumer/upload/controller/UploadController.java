package com.example.scrumer.upload.controller;

import com.example.scrumer.project.entity.Project;
import com.example.scrumer.project.service.useCase.ProjectUseCase;
import com.example.scrumer.upload.service.useCase.UploadUseCase;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/uploads")
public class UploadController {
    private final UploadUseCase uploadUseCase;
    private final ProjectUseCase projectUseCase;

//    @GetMapping("/{id}")
//    public UploadResponse getUpload(@PathVariable String id) {
//        return uploadUseCase.getById(id).map(uploadFile ->
//            new UploadResponse(uploadFile.getId(), uploadFile.getContentType(), uploadFile.getFilename(), uploadFile.getCratedAt()))
//                .orElseThrow(() -> new NotFoundException("Not found file with id: " + id));
//    }
//
//    @GetMapping("/{id}/file")
//    public ResponseEntity<Resource> getUploadFile(@PathVariable String id) {
//        return uploadUseCase.getById(id).map(uploadFile -> {
//            String contentDisposition = "attachment; filename=\"" + uploadFile.getFilename() + "\"";
//            byte[] bytes = uploadFile.getFile();
//            Resource resource = new ByteArrayResource(bytes);
//            return ResponseEntity
//                    .ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                    .contentType(MediaType.parseMediaType(uploadFile.getContentType()))
//                    .body(resource);
//
//        }).orElseThrow(() -> new NotFoundException("Not found file with id: " + id));
//    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable Long id)  {
        return uploadUseCase.getById(id).map(uploadFile -> {
            byte[] bytes = new byte[0];
            try {
                bytes = Files.readAllBytes(Path.of(uploadFile.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayResource resource = new ByteArrayResource(bytes);
            return ResponseEntity
                    .ok()
                    .contentLength(bytes.length)
                    .contentType(MediaType.parseMediaType(uploadFile.getContentType()))
                    .body(resource);

        }).orElseThrow(() -> new NotFoundException("Not found file with id: " + id));
    }

    @Value
    @AllArgsConstructor
    static class UploadResponse {
        String id;
        String contentType;
        String filename;
        LocalDateTime createdAt;
    }
}

