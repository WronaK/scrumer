package com.example.scrumer.upload.service;

import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;
import com.example.scrumer.upload.repository.UploadRepository;
import com.example.scrumer.upload.service.useCase.UploadUseCase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UploadService implements UploadUseCase {

    private final UploadRepository uploadRepository;
    private static final String catalogPath = "C:\\Users\\Kinga\\Desktop\\scrum_files\\";

    @Override
    public UploadEntity save(SaveUploadCommand command) {
        String name = RandomStringUtils.randomAlphanumeric(16).toLowerCase();
        UploadEntity upload = UploadEntity.builder()
                .filename(command.getFilename())
                .contentType(command.getContentType())
                .cratedAt(LocalDateTime.now())
                .build();

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(catalogPath + name);
            outputStream.write(command.getFile());
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        upload.setPath(catalogPath+name);
        return uploadRepository.save(upload);
    }

    @Override
    public Optional<UploadEntity> getById(Long id) {
        return uploadRepository.findById(id);
    }

    @Override
    public void removeById(Long id) {
        uploadRepository.deleteById(id);
    }
}
