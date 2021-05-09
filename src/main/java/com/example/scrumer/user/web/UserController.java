package com.example.scrumer.user.web;

import com.example.scrumer.user.application.UserService;
import com.example.scrumer.user.application.port.UserUseCase;
import com.example.scrumer.user.application.port.UserUseCase.CreateUserCommand;
import com.example.scrumer.user.application.port.UserUseCase.TeamCommand;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        System.out.println("chce pobrać");
        return userService.findById(id);
    }

    @PutMapping("/{id}/teams")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void joinTeam(@PathVariable Long id,
                         @RequestBody RestTeamCommand command) {
        userService.joinTeam(id, command.toCommand());
    }

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegisterCommand command) {
        User user = userService.register(command.toCreateCommand());
        if(user == null) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        return ResponseEntity.ok().body("User registered successfully!");
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id,
                           @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        userService.removeById(id);
    }

    @Data
    private static class RegisterCommand {
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
