package com.example.scrumer.daily.controller;

import com.example.scrumer.daily.command.CreateTaskCommand;
import com.example.scrumer.daily.command.DailyCommand;
import com.example.scrumer.daily.service.useCase.DailyUseCase;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily")
public class DailyController {
    private final DailyUseCase dailyUseCase;

    @PostMapping
    public void createTask(@RequestBody CreateTaskCommand command) {
        dailyUseCase.createTask(command);
    }

    @GetMapping("/{idTeam}")
    public DailyCommand findByIdTeam(@PathVariable Long idTeam) throws NotFoundException, ParseException, IllegalAccessException {
        return dailyUseCase.findByIdTeam(idTeam);
    }
}
