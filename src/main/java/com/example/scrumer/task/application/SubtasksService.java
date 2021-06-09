package com.example.scrumer.task.application;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.db.RealizeTaskJpaRepository;
import com.example.scrumer.task.db.SubtaskJpaRepository;
import com.example.scrumer.task.domain.RealizeTask;
import com.example.scrumer.task.domain.StatusTask;
import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.web.SubtasksController;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubtasksService implements SubtasksUseCase {
    private final SubtaskJpaRepository subtasks;
    private final UserJpaRepository userRepository;
    private final RealizeTaskJpaRepository realizeTaskRepository;

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

    @Override
    public void addTaskToRealize(SubtasksController.RestTaskRealize taskRealize, String userEmail) {
        subtasks.findById(taskRealize.getIdTask())
                .ifPresent(subtask -> {
                    userRepository.findByEmail(userEmail).ifPresent(user -> {
                        RealizeTask realizeTask = RealizeTask.builder()
                                .user(user)
                                .subtask(subtask)
                                .state(StatusTask.parseString(taskRealize.getStatus()).orElseThrow())
                        .build();
                        realizeTaskRepository.save(realizeTask);
                        subtask.addRealizeTask(realizeTask, user);
                        subtasks.save(subtask);
                    });
                });
    }

    @Override
    public List<Subtask> findByEmail(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return user.map(value -> realizeTaskRepository.findRealizeTasksByUser(value)
                .stream()
                .filter(realizeTask -> realizeTask.getState()==realizeTask.getSubtask().getStatusTask())
                .map(RealizeTask::getSubtask)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
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
