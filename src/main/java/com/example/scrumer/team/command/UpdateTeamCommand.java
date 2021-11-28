package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTeamCommand {
    private Long id;
    private String teamName;
    private String accessCode;
    private String description;
    private Long scrumMaster;
}
