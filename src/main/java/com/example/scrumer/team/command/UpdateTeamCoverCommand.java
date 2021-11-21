package com.example.scrumer.team.command;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeamCoverCommand {
    private Long id;
    private byte[] file;
    private String contentType;
    private String filename;
}
