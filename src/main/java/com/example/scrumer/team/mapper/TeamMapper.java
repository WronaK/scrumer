package com.example.scrumer.team.mapper;

import com.example.scrumer.team.command.TeamInformationCommand;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.command.TeamDetailsCommand;
import com.example.scrumer.team.command.TeamCommand;
import com.example.scrumer.user.command.AttachmentCommand;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
                .description(team.getDescription())
                .username(team.getScrumMaster().getUserDetails().getUsername())
                .coverId(team.getCoverId())
                .build();
    }

    public static TeamInformationCommand toTeamInformationCommand(Team team) {
        return TeamInformationCommand.builder()
                .id(team.getId())
                .name(team.getTeamName())
                .description(team.getDescription())
                .accessCode(team.getAccessCode())
                .username(team.getScrumMaster().getUserDetails().getUsername())
                .coverId(team.getCoverId())
                .attachments(team.getAttachments().stream().map(attachment -> new AttachmentCommand(attachment.getId(), attachment.getFilename())).collect(Collectors.toList()))
                .build();
    }
}
