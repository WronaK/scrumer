package com.example.scrumer.project.command;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectCoverCommand {
    private Long id;
    private byte[] file;
    private String contentType;
    private String filename;
}
