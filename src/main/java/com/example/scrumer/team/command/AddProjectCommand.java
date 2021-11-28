package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddProjectCommand {
    private Long idProject;
    private String accessCode;
}
