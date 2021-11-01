package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTeamCommand {
    private Long id;
    private String name;
    private String accessCode;
}
