package com.example.scrumer.task.application;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.db.SubtaskJpaRepository;
import com.example.scrumer.task.domain.StatusTask;
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

    @Override
    public void changeStatus(Long id) {
        subtasks.findById(id).ifPresent(subtask -> {
            this.status(subtask);
            subtasks.save(subtask);
        });
    }

    private void status(Subtask subtask) {
        switch (subtask.getStatusTask()) {
            case NEW_TASK:
                subtask.setStatusTask(StatusTask.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                subtask.setStatusTask(StatusTask.MERGE_REQUEST);
                break;
            case MERGE_REQUEST:
                subtask.setStatusTask(StatusTask.DONE);
                break;
        }
    }
}
