package com.example.scrumer.upload.service.useCase;

import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;

import java.util.Optional;

public interface UploadUseCase {
    UploadEntity save(SaveUploadCommand command);

    Optional<UploadEntity> getById(Long id);

    void removeById(Long id);
}
