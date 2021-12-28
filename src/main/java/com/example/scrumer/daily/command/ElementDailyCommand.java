package com.example.scrumer.daily.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ElementDailyCommand {
    private Long idUser;
    private String username;
    private TasksCommand yesterdayTasks;
    private TasksCommand todayTasks;
}
