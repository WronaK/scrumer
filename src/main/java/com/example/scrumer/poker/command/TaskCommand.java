package com.example.scrumer.poker.command;

import com.example.scrumer.issue.entity.TypeIssue;
import com.example.scrumer.issue.entity.TypeTask;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskCommand {
    private Long idTask;
    private TypeTask typeTask;
}
