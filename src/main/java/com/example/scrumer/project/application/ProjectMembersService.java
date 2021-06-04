package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectMembersUseCase;
import com.example.scrumer.project.application.port.ProjectsUseCase.TeamCommand;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectMembersService implements ProjectMembersUseCase {
    private final ProjectJpaRepository repository;
    private final TeamJpaRepository teamsRepository;

    @Override
    public void addTeams(Long id, Set<TeamCommand> command) {
        repository.findById(id).ifPresent(project -> {
            this.addTeams(project, command);
            repository.save(project);
        });
    }

    @Override
    public List<Team> findTeams(Long id) {
        return repository.findTeams(id);
    }

    @Override
    public void removeTeam(Long id, Long idTeam) {
        repository.findById(id).ifPresent(project ->
                teamsRepository.findById(idTeam)
                        .ifPresent(team -> {
                            project.removeTeam(team);
                            repository.save(project);
                        }));
    }

    void addTeams(Project project, Set<TeamCommand> commands) {
        for (TeamCommand command: commands) {
            this.addTeam(project, command);
        }
    }

    private void addTeam(Project project, TeamCommand command) {
        teamsRepository.findTeamByNameAndAccessCode(command.getName(), command.getAccessCode())
                .ifPresent(project::addTeam);
    }

}
