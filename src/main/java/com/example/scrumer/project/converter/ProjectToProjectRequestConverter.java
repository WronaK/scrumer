package com.example.scrumer.project.converter;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.project.request.ProjectRequest;
import org.springframework.stereotype.Component;

@Component
public class ProjectToProjectRequestConverter {
    public ProjectRequest toDto(Project project) {
        return ProjectRequest.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
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
}
