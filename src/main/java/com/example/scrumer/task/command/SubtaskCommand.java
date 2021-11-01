package com.example.scrumer.task.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SubtaskCommand {
    private Set<CreateTaskCommand> tasks;
}
