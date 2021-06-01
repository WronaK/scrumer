package com.example.scrumer.task.web;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.converter.TaskToTaskRequestConverter;
import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.request.TaskRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subtasks")
public class SubtasksController {
    private final SubtasksUseCase subtasks;
    private final TaskToTaskRequestConverter tasksConverter;

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
}
