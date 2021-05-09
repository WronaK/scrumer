package com.example.scrumer.security;

import lombok.Data;

@Data
public class LoginCommand {
    private String email;
    private String password;
}
