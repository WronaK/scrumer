package com.example.scrumer.team.application.port;

import com.example.scrumer.user.domain.User;

import java.util.List;
import java.util.Set;

public interface TeamMembersUseCase {
    void addMember(Long id, Set<TeamsUseCase.MemberCommand> command);

    List<User> findMembersById(Long id);
}
