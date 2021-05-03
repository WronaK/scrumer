package com.example.scrumer.task.web;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.domain.Subtask;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/subtasks")
public class SubtasksController {
    private final SubtasksUseCase subtasks;

    @GetMapping("/{id}")
    public Optional<Subtask> getSubtaskById(@PathVariable Long id) {
        return subtasks.findById(id);
    }
}
