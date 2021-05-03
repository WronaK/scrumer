package com.example.scrumer.task.application.port;

import com.example.scrumer.task.domain.Subtask;

import java.util.Optional;

public interface SubtasksUseCase {
    Optional<Subtask> findById(Long id);
}
