package com.example.scrumer.daily.service.useCase;

import com.example.scrumer.daily.command.CreateTaskCommand;
import com.example.scrumer.daily.command.DailyCommand;
import javassist.NotFoundException;

import java.text.ParseException;

public interface DailyUseCase {
    void createTask(CreateTaskCommand command);

    DailyCommand findByIdTeam(Long idTeam) throws ParseException, NotFoundException, IllegalAccessException;
}