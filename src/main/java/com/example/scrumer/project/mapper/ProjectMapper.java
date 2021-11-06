package com.example.scrumer.project.mapper;

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
                .name(project.getName())
                .description(project.getDescription())
                .accessCode(project.getAccessCode())
                .creatorName(project.getCreator().getUserDetails().getName() +
                        ' ' + project.getCreator().getUserDetails().getSurname())
                .productOwnerName(project.getProductOwner() != null ?
                        project.getProductOwner().getUserDetails().getName() +
                                ' ' + project.getProductOwner().getUserDetails().getSurname() : "")
                .scrumMasterName(project.getScrumMaster() != null ?
                        project.getScrumMaster().getUserDetails().getName() +
                                ' ' + project.getScrumMaster().getUserDetails().getSurname(): "")
                .build();
    }

    public static UpdateProjectCommand toUpdateProjectCommand(Project project) {
        return UpdateProjectCommand.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .accessCode(project.getAccessCode())
                .productOwner(project.getProductOwner()!= null ? project.getProductOwner().getEmail() : "")
                .scrumMaster(project.getScrumMaster()!= null ? project.getScrumMaster().getEmail(): "")
                .build();
    }

    public static ProjectCommand toProjectCommand(Project project) {
        return ProjectCommand.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }
}
