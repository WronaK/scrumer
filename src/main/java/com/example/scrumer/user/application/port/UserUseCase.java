package com.example.scrumer.user.application.port;

import com.example.scrumer.user.domain.User;
import lombok.Value;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    Optional<User> findById(Long id);

    User register(CreateUserCommand command);

    void removeById(Long id);

    List<User> findAll();

    void joinTeam(Long id, TeamCommand command);

    @Value
    class CreateUserCommand {
        String name;
        String surname;
        String email;
        String password;
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }
}
