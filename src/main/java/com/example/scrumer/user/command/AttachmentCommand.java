package com.example.scrumer.user.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentCommand {
    private Long id;
    private String filename;
}
