package com.example.scrumer.project.converter;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.project.request.ProjectRequest;
import com.example.scrumer.project.request.ProjectShortcutRequest;
import com.example.scrumer.project.request.UpdateProjectRequest;
import org.springframework.stereotype.Component;

@Component
public class ProjectToProjectRequestConverter {
    public ProjectRequest toDto(Project project) {
        return ProjectRequest.builder()
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

    public UpdateProjectRequest toDtoUpdate(Project project) {
        return UpdateProjectRequest.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .accessCode(project.getAccessCode())
                .productOwner(project.getProductOwner()!= null ? project.getProductOwner().getEmail() : "")
                .scrumMaster(project.getScrumMaster()!= null ? project.getScrumMaster().getEmail(): "")
                .build();
    }

    public ProjectShortcutRequest toDtoShortcut(Project project) {
        return ProjectShortcutRequest.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }
}
