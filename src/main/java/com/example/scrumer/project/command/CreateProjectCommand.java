package com.example.scrumer.project.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateProjectCommand {
    private String projectName;
    private String accessCode;
    private String description;
    private Long productOwner;
}
