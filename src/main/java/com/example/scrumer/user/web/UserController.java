package com.example.scrumer.user.web;

import com.example.scrumer.user.application.UserService;
import com.example.scrumer.user.application.port.UserUseCase.CreateUserCommand;
import com.example.scrumer.user.application.port.UserUseCase.TeamCommand;
import com.example.scrumer.user.converter.UserToUserRequestConverter;
import com.example.scrumer.user.domain.User;
import com.example.scrumer.user.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserToUserRequestConverter userConverter;

    @GetMapping
    public ResponseEntity<UserRequest> getLoggedUser() {
        return userService.findByEmail(this.getUserEmail())
                .map(user -> ResponseEntity.ok(this.userConverter.toDto(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
