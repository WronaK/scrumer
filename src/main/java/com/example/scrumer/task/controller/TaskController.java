package com.example.scrumer.task.controller;

import com.example.scrumer.task.command.SubtaskCommand;
import com.example.scrumer.task.command.TaskCommand;
import com.example.scrumer.task.mapper.TaskMapper;
import com.example.scrumer.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService tasks;

    @GetMapping
    public List<TaskCommand> getAll() {
        return tasks.findAll().stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskCommand> getById(@PathVariable Long id) {
        return tasks.findById(id).map(task -> ResponseEntity.ok(TaskMapper.toDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping()
    public void updateTaskById(@RequestBody TaskCommand task) {
        tasks.updateTask(task);
    }

    @PutMapping("/{id}/subtasks")
    public void addSubtasks(@PathVariable Long id,
                           @RequestBody SubtaskCommand command) {
        tasks.addSubtasks(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tasks.deleteById(id);
    }

}
