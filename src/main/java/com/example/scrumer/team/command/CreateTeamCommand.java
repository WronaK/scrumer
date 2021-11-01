package com.example.scrumer.team.command;

import com.example.scrumer.team.controller.TeamController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CreateTeamCommand {
    private String name;
    private String accessCode;
    private Set<MemberTeamCommand> members;
}
