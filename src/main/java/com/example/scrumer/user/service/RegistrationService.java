package com.example.scrumer.user.service;

import com.example.scrumer.user.command.RegisterCommand;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.entity.UserDetails;
import com.example.scrumer.user.repository.UserDetailsJpaRepository;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.service.useCase.RegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegistrationUseCase {
    private final UserJpaRepository repository;
    private final UserDetailsJpaRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterCommand command) {
        if (repository.findByEmail(command.getEmail()).isPresent()) {
            throw new ValidationException("Email is already in use!");
        }

        UserDetails userDetails = userDetailsRepository.save(new UserDetails(command.getName(), command.getSurname()));

        User user = new User(
                command.getEmail(),
                passwordEncoder.encode(command.getPassword()),
                userDetails);

        return repository.save(user);
    }
}
