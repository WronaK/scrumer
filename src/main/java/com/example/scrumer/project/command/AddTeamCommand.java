package com.example.scrumer.project.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddTeamCommand {
    private String name;
    private String accessCode;
}
