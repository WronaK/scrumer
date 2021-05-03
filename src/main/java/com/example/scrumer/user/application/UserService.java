package com.example.scrumer.user.application;

import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.application.port.UserUseCase;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserJpaRepository repository;
    private final TeamJpaRepository teamRepository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User save(CreateUserCommand command) {
        User user = command.toUser();
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
    public void joinTeam(Long id, TeamCommand command) {
        repository.findById(id)
                .ifPresent(user ->
                {
                    Team team = teamRepository.findByNameAndAccessCode(command.getName(), command.getAccessCode());
                    team.addMember(user);
                    teamRepository.save(team);
                    user.addTeam(team);
                    repository.save(user);
                });
    }
}
