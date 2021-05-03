package com.example.scrumer.task.application;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.db.SubtaskJpaRepository;
import com.example.scrumer.task.domain.Subtask;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SubtasksService implements SubtasksUseCase {
    private final SubtaskJpaRepository subtasks;

    @Override
    public Optional<Subtask> findById(Long id) {
        return subtasks.findById(id);
    }
}
