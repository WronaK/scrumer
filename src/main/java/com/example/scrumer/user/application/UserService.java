package com.example.scrumer.user.application;

import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.application.port.UserUseCase;
import com.example.scrumer.user.db.UserDetailsJpaRepository;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import com.example.scrumer.user.domain.UserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserJpaRepository repository;
    private final TeamJpaRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsJpaRepository userDetailsRepository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User register(CreateUserCommand command) throws ValidationException {
        if(repository.findByEmail(command.getEmail()).isPresent()) {
            throw new ValidationException("Error: Email is already in use!");
        }

        UserDetails userDetails = userDetailsRepository.save(new UserDetails(command.getName(), command.getSurname()));

        User user = new User(
                command.getEmail(),
                passwordEncoder.encode(command.getPassword()),
                userDetails);

        return repository.save(user);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void joinTeam(String userEmail, TeamCommand command) {
        Optional<User> user = repository.findByEmail(userEmail);

        if(user.isPresent()) {
            User savedUser = user.get();
            teamRepository.findTeamByNameAndAccessCode(command.getName(), command.getAccessCode())
                    .ifPresent(team -> {
                        savedUser.addTeam(team);
                        repository.save(savedUser);
                    });
        }
    }

    @Override
    public void addTeam(Long id, TeamCommand command) {
        repository.findById(id)
                .ifPresent(user ->
                {
                    teamRepository.findTeamByNameAndAccessCode(command.getName(), command.getAccessCode()).ifPresent(
                            team -> {
//                                team.addMember(user);
//                                teamRepository.save(team);
                                user.addTeam(team);
                                repository.save(user);
                            }
                    );
                });
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
}
