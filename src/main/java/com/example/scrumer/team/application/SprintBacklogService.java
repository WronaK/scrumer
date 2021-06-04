package com.example.scrumer.team.application;

import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.db.TaskJpaRepository;
import com.example.scrumer.task.domain.StatusTask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.application.port.SprintBacklogUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SprintBacklogService implements SprintBacklogUseCase {
    private final TeamJpaRepository repository;
    private final ProjectJpaRepository projectRepository;
    private final TaskJpaRepository tasksRepository;

    @Override
    public List<Task> getSprintBacklog(Long id) {
        return repository.getSprintBacklog(id);
    }

    @Override
    public List<Project> findProjectsById(Long id) {
        return repository.findProjects(id);
    }

    @Override
    public void addProject(Long id, ProjectCommand command) {
        repository.findById(id)
                .ifPresent(team -> {
                    Project project = projectRepository
                            .findByNameAndAccessCode(command.getName(), command.getAccessCode());
                    project.addTeam(team);
                    repository.save(team);
                });
    }

    @Override
    public void addTask(Long id, Long idTask) {
        repository.findById(id)
                .ifPresent(team -> {
                    tasksRepository.findById(idTask)
                            .ifPresent(task -> {
                                task.setStatusTask(StatusTask.FOR_IMPLEMENTATION);
                                team.addTaskToSprintBacklog(task);
                                repository.save(team);
                            });
                });
    }
}
