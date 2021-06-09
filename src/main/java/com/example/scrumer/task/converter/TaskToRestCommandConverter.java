package com.example.scrumer.task.converter;

import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.request.TaskRequest;
import org.springframework.stereotype.Component;

@Component
public class TaskToRestCommandConverter {
    public TaskRequest toDto(Task task) {
        return TaskRequest.builder()
                .id(task.getId())
                .title(task.getTaskDetails().getTitle())
                .description(task.getTaskDetails().getDescription())
                .priority(task.getTaskDetails().getPriority())
                .storyPoints(task.getTaskDetails() != null ? task.getTaskDetails().getStoryPoints(): null)
                .status(task.getStatusTask().toString().replace('_', ' '))
                .build();
    }

    public TaskRequest toDto(Subtask task) {
        return TaskRequest.builder()
                .id(task.getId())
                .title(task.getTaskDetails().getTitle())
                .description(task.getTaskDetails().getDescription())
                .priority(task.getTaskDetails().getPriority())
                .storyPoints(task.getTaskDetails() != null ? task.getTaskDetails().getStoryPoints(): null)
                .status(task.getStatusTask().toString().replace('_', ' '))
                .build();
    }
}
