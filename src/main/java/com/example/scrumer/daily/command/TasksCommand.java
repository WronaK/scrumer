package com.example.scrumer.daily.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TasksCommand {
    private Date date;
    private List<TaskCommand> tasks;
}
