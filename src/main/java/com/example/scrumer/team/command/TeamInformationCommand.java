package com.example.scrumer.team.command;

import com.example.scrumer.user.command.AttachmentCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class TeamInformationCommand {
    private Long id;
    private String name;
    private String accessCode;
    private String description;
    private String username;
    private Long coverId;
    private List<AttachmentCommand> attachments;
}

