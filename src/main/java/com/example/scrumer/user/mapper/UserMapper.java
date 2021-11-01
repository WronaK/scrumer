package com.example.scrumer.user.mapper;

import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.command.UserCommand;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserCommand toUserCommand(User user) {
        return UserCommand.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUserDetails().getName())
                .surname(user.getUserDetails().getSurname())
                .roles(user.getRoles())
                .build();
    }
}
