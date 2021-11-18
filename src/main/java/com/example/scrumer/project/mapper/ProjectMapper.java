package com.example.scrumer.project.mapper;

import com.example.scrumer.project.command.ProjectInformationCommand;
import com.example.scrumer.project.entity.Project;
import com.example.scrumer.project.command.ProjectDetailsCommand;
import com.example.scrumer.project.command.ProjectCommand;
import com.example.scrumer.project.command.UpdateProjectCommand;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public static ProjectDetailsCommand toProjectDetailsCommand(Project project) {
        return ProjectDetailsCommand.builder()
                .id(project.getId())
                .name(project.getProjectName())
                .description(project.getDescription())
                .username(project.getProductOwner().getUserDetails().getUsername())
                .build();
    }

    public static UpdateProjectCommand toUpdateProjectCommand(Project project) {
        return UpdateProjectCommand.builder()
                .id(project.getId())
                .name(project.getProjectName())
                .description(project.getDescription())
                .accessCode(project.getAccessCode())
                .productOwner(project.getProductOwner().getUserDetails().getUsername())
                .build();
    }

    public static ProjectCommand toProjectCommand(Project project) {
        return ProjectCommand.builder()
                .id(project.getId())
                .name(project.getProjectName())
                .build();
    }

    public static ProjectInformationCommand toProjectInformationCommand(Project project) {
        return ProjectInformationCommand.builder()
                .id(project.getId())
                .name(project.getProjectName())
                .accessCode(project.getAccessCode())
                .description(project.getDescription())
                .username(project.getProductOwner().getUserDetails().getUsername())
                .build();
    }
}
