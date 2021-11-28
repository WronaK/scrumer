package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserStoryCommand {
    private String title;
    private String description;
    private PriorityStatus priority;
}
