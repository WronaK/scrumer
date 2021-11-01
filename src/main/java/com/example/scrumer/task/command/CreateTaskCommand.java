package com.example.scrumer.task.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTaskCommand {
    private String title;
    private String description;
    private String priority;
}
