package com.example.scrumer.task.application.port;

import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.web.SubtasksController;

import java.util.List;
import java.util.Optional;

public interface SubtasksUseCase {
    Optional<Subtask> findById(Long id);

    void changeStatus(Long id);

    void addTaskToRealize(SubtasksController.RestTaskRealize taskRealize, String userEmail);

    List<Subtask> findByEmail(String userEmail);
}
