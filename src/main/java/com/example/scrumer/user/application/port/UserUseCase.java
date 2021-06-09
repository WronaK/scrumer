package com.example.scrumer.user.application.port;

import com.example.scrumer.user.domain.User;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    Optional<User> findById(Long id);

    User register(CreateUserCommand command);

    void removeById(Long id);

    List<User> findAll();

    void joinTeam(Long id, TeamCommand command);

    Optional<User> findByEmail(String userEmail);

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
}
