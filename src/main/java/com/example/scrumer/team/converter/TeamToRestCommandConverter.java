package com.example.scrumer.team.converter;

import com.example.scrumer.team.domain.Team;
import com.example.scrumer.team.request.TeamDetails;
import com.example.scrumer.team.request.TeamRequest;
import org.springframework.stereotype.Component;

@Component
public class TeamToRestCommandConverter {

    public TeamRequest toDto(Team team) {
        return TeamRequest.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public TeamDetails toDtoDetails(Team team) {
        return TeamDetails.builder()
                .id(team.getId())
                .name(team.getName())
                .accessCode(team.getAccessCode())
                .creatorName(team.getCreator().getUserDetails().getName() + ' ' + team.getCreator().getUserDetails().getSurname())
                .build();
    }
}
