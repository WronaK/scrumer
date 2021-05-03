package com.example.scrumer.user.web;

import com.example.scrumer.user.application.UserService;
import com.example.scrumer.user.application.port.UserUseCase;
import com.example.scrumer.user.application.port.UserUseCase.CreateUserCommand;
import com.example.scrumer.user.application.port.UserUseCase.TeamCommand;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService users;

    @GetMapping
    public List<User> getAll() {
        return users.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        return users.findById(id);
    }

    @PostMapping
    public void createUser(@RequestBody RestUserCommand command) {
        users.save(command.toCreateCommand());
    }

    @PutMapping("/{id}/teams")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void joinTeam(@PathVariable Long id, @RequestBody RestTeamCommand command) {
        users.joinTeam(id, command.toCommand());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        users.removeById(id);
    }

    @Data
    private static class RestUserCommand {
        private String name;
        private String surname;
        private String email;
        private String password;

        CreateUserCommand toCreateCommand() {
            return new CreateUserCommand(name, surname, email, password);
        }
    }

    @Data
    private static class RestTeamCommand {
        private String name;
        private String accessCode;

        TeamCommand toCommand() {
            return new TeamCommand(name, accessCode);
        }
    }
}
