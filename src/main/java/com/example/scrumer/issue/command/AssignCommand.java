package com.example.scrumer.issue.command;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignCommand {
    private Long imageId;
    private Long userId;
    private String username;
    private String surname;
}
