package com.example.scrumer.user.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserFindCommand {
    private Long id;
    private String email;
    private String username;
}
