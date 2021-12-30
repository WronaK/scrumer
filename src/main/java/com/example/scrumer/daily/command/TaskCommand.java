package com.example.scrumer.daily.command;

import com.example.scrumer.daily.model.TypeTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TaskCommand {
    private String title;
    private Long idTask;
    private TypeTask typeTask;
}
