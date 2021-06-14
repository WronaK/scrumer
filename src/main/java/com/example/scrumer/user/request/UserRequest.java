package com.example.scrumer.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {
    private String email;
    private String name;
    private String surname;
    private Set<String> roles;
}
