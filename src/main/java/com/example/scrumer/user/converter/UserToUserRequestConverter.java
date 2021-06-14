package com.example.scrumer.user.converter;

import com.example.scrumer.user.domain.User;
import com.example.scrumer.user.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserToUserRequestConverter {

    public UserRequest toDto(User user) {
        return UserRequest.builder()
                .email(user.getEmail())
                .name(user.getUserDetails().getName())
                .surname(user.getUserDetails().getSurname())
                .roles(user.getRoles())
                .build();
    }
}
