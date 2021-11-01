package com.example.scrumer.task.controller;

import com.example.scrumer.task.service.useCase.SubtasksUseCase;
import com.example.scrumer.task.mapper.TaskMapper;
import com.example.scrumer.task.entity.Subtask;
import com.example.scrumer.task.command.TaskCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subtasks")
public class SubtaskController {
    private final SubtasksUseCase subtasks;

    @GetMapping
    public List<TaskCommand> getSubtasks() {
        return subtasks.findByEmail(this.getUserEmail())
                .stream().map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskCommand getSubtasksById(@PathVariable Long id) {
        Optional<Subtask> task = subtasks.findById(id);
        return task.map(TaskMapper::toDto).orElseThrow(() -> new IllegalArgumentException("Not found task id: " + id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeStatusSubtasks(@PathVariable Long id) {
        subtasks.changeStatus(id);
    }

    @PatchMapping("/realize")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTaskToRealize(@RequestBody RestTaskRealize taskRealize) {
        subtasks.addTaskToRealize(taskRealize, this.getUserEmail());
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @Data
    public static class RestTaskRealize {
        private Long idTask;
        private String status;
    }
}
