package com.example.scrumer.task.web;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.converter.TaskToTaskRequestConverter;
import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.request.TaskRequest;
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
public class SubtasksController {
    private final SubtasksUseCase subtasks;
    private final TaskToTaskRequestConverter tasksConverter;

    @GetMapping
    public List<TaskRequest> getSubtasks() {
        return subtasks.findByEmail(this.getUserEmail())
                .stream().map(tasksConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskRequest getSubtaskById(@PathVariable Long id) {
        Optional<Subtask> task = subtasks.findById(id);
        return task.map(tasksConverter::toDto).orElseThrow(() -> new IllegalArgumentException("Not found task id: " + id));
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
