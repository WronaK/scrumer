package com.example.scrumer.team.application;

import com.example.scrumer.team.application.port.TeamMembersUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeamMembersService implements TeamMembersUseCase {
    private final TeamJpaRepository repository;
    private final UserJpaRepository userRepository;

    @Override
    public void addMember(Long id, Set<TeamsUseCase.MemberCommand> command) {
        repository.findById(id).ifPresent(team -> {
            team.addMembers(this.fetchUserByEmail(command));
            repository.save(team);
        });
    }

    @Override
    public List<User> findMembersById(Long id) {
        return repository.findMembers(id);
    }

    protected Set<User> fetchUserByEmail(Set<TeamsUseCase.MemberCommand> members) {
        return members.stream()
                .map(member -> userRepository.findByEmail(member.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("Not found user by email: " + member.getEmail()))
                ).collect(Collectors.toSet());
    }
}
