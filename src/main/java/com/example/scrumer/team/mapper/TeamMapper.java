package com.example.scrumer.team.mapper;

import com.example.scrumer.team.command.TeamInformationCommand;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.command.TeamDetailsCommand;
import com.example.scrumer.team.command.TeamCommand;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public static TeamCommand toCommand(Team team) {
        return TeamCommand.builder()
                .id(team.getId())
                .name(team.getTeamName())
                .description(team.getDescription())
                .username(team.getScrumMaster().getUserDetails().getUsername())
                .build();
    }

    public static TeamDetailsCommand toTeamDetailsCommand(Team team) {
        return TeamDetailsCommand.builder()
                .id(team.getId())
                .name(team.getTeamName())
                .accessCode(team.getAccessCode())
                .build();
    }

    public static TeamInformationCommand toTeamInformationCommand(Team team) {
        return TeamInformationCommand.builder()
                .id(team.getId())
                .name(team.getTeamName())
                .description(team.getDescription())
                .accessCode(team.getAccessCode())
                .username(team.getScrumMaster().getUserDetails().getUsername())
                .build();
    }
}
