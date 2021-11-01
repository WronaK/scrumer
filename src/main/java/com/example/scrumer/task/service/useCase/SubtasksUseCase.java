package com.example.scrumer.task.service.useCase;

import com.example.scrumer.task.entity.Subtask;
import com.example.scrumer.task.controller.SubtaskController;

import java.util.List;
import java.util.Optional;

public interface SubtasksUseCase {
    Optional<Subtask> findById(Long id);

    void changeStatus(Long id);

    void addTaskToRealize(SubtaskController.RestTaskRealize taskRealize, String userEmail);

    List<Subtask> findByEmail(String userEmail);
}
