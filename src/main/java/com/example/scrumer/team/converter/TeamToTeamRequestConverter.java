package com.example.scrumer.team.converter;

import com.example.scrumer.team.domain.Team;
import com.example.scrumer.team.request.TeamRequest;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamRequestConverter {

    public TeamRequest toDto(Team team) {
        return TeamRequest.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }
}
