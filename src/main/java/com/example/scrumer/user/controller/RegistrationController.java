package com.example.scrumer.user.controller;

import com.example.scrumer.user.command.RegisterCommand;
import com.example.scrumer.user.service.useCase.RegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class RegistrationController {
    private final RegistrationUseCase registrationUseCase;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterCommand command) {
        registrationUseCase.register(command);
        return ResponseEntity.ok().build();
    }
}
