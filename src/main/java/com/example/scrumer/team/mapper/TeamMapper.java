package com.example.scrumer.team.mapper;

import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.command.TeamDetailsCommand;
import com.example.scrumer.team.command.TeamCommand;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public static TeamCommand toCommand(Team team) {
        return TeamCommand.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public static TeamDetailsCommand toTeamDetailsCommand(Team team) {
        return TeamDetailsCommand.builder()
                .id(team.getId())
                .name(team.getName())
                .accessCode(team.getAccessCode())
                .creatorName(team.getCreator().getUserDetails().getName() + ' ' + team.getCreator().getUserDetails().getSurname())
                .build();
    }
}
