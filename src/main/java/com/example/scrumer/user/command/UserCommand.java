package com.example.scrumer.user.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserCommand {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Set<String> roles;
    private Long idProfileImage;
}
