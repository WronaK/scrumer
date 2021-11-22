package com.example.scrumer.project.mapper;

import com.example.scrumer.project.command.ProjectInformationCommand;
import com.example.scrumer.project.entity.Project;
import com.example.scrumer.project.command.ProjectDetailsCommand;
import com.example.scrumer.project.command.ProjectCommand;
import com.example.scrumer.project.command.UpdateProjectCommand;
import com.example.scrumer.user.command.AttachmentCommand;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper {
    public static ProjectDetailsCommand toProjectDetailsCommand(Project project) {
        return ProjectDetailsCommand.builder()
                .id(project.getId())
                .name(project.getProjectName())
                .description(project.getDescription())
                .username(project.getProductOwner().getUserDetails().getUsername())
                .coverId(project.getCoverId())
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
                .coverId(project.getCoverId())
                .attachments(project.getAttachments().stream().map(attachment -> new AttachmentCommand(attachment.getId(), attachment.getFilename())).collect(Collectors.toList()))
                .build();
    }
}
