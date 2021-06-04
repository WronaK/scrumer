package com.example.scrumer.project.application.port;

import com.example.scrumer.team.domain.Team;

import java.util.List;
import java.util.Set;

import static com.example.scrumer.project.application.port.ProjectsUseCase.*;

public interface ProjectMembersUseCase {
    void addTeams(Long id, Set<TeamCommand> command);

    List<Team> findTeams(Long id);

    void removeTeam(Long id, Long idTeam);
}
