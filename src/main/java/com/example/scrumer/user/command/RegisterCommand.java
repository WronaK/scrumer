package com.example.scrumer.user.command;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegisterCommand {
    private String name;
    private String surname;
    private String email;
    private String password;
}
