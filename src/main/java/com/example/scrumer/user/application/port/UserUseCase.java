package com.example.scrumer.user.application.port;

import com.example.scrumer.user.domain.User;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    Optional<User> findById(Long id);

    User save(CreateUserCommand command);

    void removeById(Long id);

    List<User> findAll();

    void joinTeam(Long id, TeamCommand command);

    @Value
    class CreateUserCommand {
        String name;
        String surname;
        String email;
        String password;

        public User toUser() {
            return new User(name, surname, email, password);
        }
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }
}
