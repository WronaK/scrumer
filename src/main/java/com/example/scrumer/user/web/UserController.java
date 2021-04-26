package com.example.scrumer.user.web;

import com.example.scrumer.user.application.UserService;
import com.example.scrumer.user.application.port.UserUseCase.CreateUserCommand;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public void createUser(@RequestBody RestUserCommand command) {
        userService.save(command.toCreateCommand());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.removeById(id);
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
}
