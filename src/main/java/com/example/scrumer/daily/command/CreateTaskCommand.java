package com.example.scrumer.daily.command;

import com.example.scrumer.daily.model.TypeTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskCommand {
    private Long userId;
    private Long teamId;
    private String titleTask;
    private TypeTask typeTask;
    private Long taskId;
}
