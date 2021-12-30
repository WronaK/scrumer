package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamCommand {
    private String teamName;
    private String accessCode;
    private String description;
    private Long scrumMaster;
}
