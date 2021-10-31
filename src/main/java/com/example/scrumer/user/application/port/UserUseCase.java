package com.example.scrumer.user.application.port;

import com.example.scrumer.chat.model.ChannelDto;
import com.example.scrumer.user.domain.User;
import lombok.Value;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    Optional<User> findById(Long id);

    User register(CreateUserCommand command) throws ValidationException;

    void removeById(Long id);

    List<User> findAll();

    void joinTeam(String userEmail, TeamCommand command);

    Optional<User> findByEmail(String userEmail);

    void addTeam(Long id, TeamCommand command);

    List<ChannelDto> getChannels(Long id);

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
