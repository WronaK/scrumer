package com.example.scrumer.upload.service.useCase;

import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.Upload;

import java.util.Optional;

public interface UploadUseCase {
    Upload save(SaveUploadCommand command);

    Optional<Upload> getById(String id);

    void removeById(String coverId);
}
