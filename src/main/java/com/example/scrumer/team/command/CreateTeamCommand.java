package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamCommand {
    private String name;
    private String accessCode;
    private Set<MemberTeamCommand> members;
}
