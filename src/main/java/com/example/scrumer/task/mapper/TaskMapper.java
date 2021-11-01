package com.example.scrumer.task.mapper;

import com.example.scrumer.task.entity.Subtask;
import com.example.scrumer.task.entity.Task;
import com.example.scrumer.task.command.TaskCommand;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public static TaskCommand toDto(Task task) {
        return TaskCommand.builder()
                .id(task.getId())
                .title(task.getTaskDetails().getTitle())
                .description(task.getTaskDetails().getDescription())
                .priority(task.getTaskDetails().getPriority().toString())
                .storyPoints(task.getTaskDetails() != null ? task.getTaskDetails().getStoryPoints(): null)
                .status(task.getStatusTask().toString().replace('_', ' '))
                .build();
    }

    public static TaskCommand toDto(Subtask task) {
        return TaskCommand.builder()
                .id(task.getId())
                .title(task.getTaskDetails().getTitle())
                .description(task.getTaskDetails().getDescription())
                .priority(task.getTaskDetails().getPriority().toString())
                .storyPoints(task.getTaskDetails() != null ? task.getTaskDetails().getStoryPoints(): null)
                .status(task.getStatusTask().toString().replace('_', ' '))
                .build();
    }
}
