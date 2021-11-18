package com.example.scrumer.security;

import com.example.scrumer.project.entity.Project;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.user.entity.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class ValidatorPermission {

    public boolean isCreatorOrAdmin(String objectOwner, UserPrincipal user) {
        return isAdmin(user) || isCreator(objectOwner, user);
    }

    private boolean isCreator(String objectOwner, UserPrincipal user) {
        return user.getUsername().equalsIgnoreCase(objectOwner);
    }

    private boolean isAdmin(UserPrincipal user) {
        return user.getAuthorities()
                .stream()
                .anyMatch(u -> u.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
    }

    private boolean isMemberTeam(Set<User> members, String email) {
        for(User userMember: members) {
            if(userMember.getEmail().equals(email))
                return true;
        }
        return false;
    }

    private boolean isScrumMaster(Team team, String email) {
        return email.equals(team.getScrumMaster().getEmail());
    }

    public void validateModifyTeamPermission(Optional<Team> team, String email) throws IllegalAccessException, NotFoundException {
        if(team.isPresent()) {
            Team myTeam = team.get();

            if(isScrumMaster(myTeam, email)) {
                return;
            }

            throw new IllegalAccessException("Illegal team permission exception");
        }
        throw new NotFoundException("Not found team.");
    }

    public void validateTeamPermission(Team team, String email) throws IllegalAccessException {
        if(isScrumMaster(team, email)) {
            return;
        }

        if(this.isMemberTeam(team.getMembers(), email)) {
            return;
        }

        throw new IllegalAccessException("Illegal team permission exception");
    }

    public void validateModifyProjectPermission(Project project, String email) throws IllegalAccessException {
        if (email.equals(project.getProductOwner() .getEmail())) {
            return;
        }

        throw new IllegalAccessException("Illegal project permission exception");
    }

    public void validateProjectPermission(Project project, String email) throws IllegalAccessException {
        if (email.equals(project.getProductOwner().getEmail())) {
            return;
        }

        for (Team team : project.getTeams()) {
            if (email.equals(team.getScrumMaster().getEmail()) || this.isMemberTeam(team.getMembers(), email)) {
                return;
            }
        }
        throw new IllegalAccessException("Illegal project permission exception");
    }
}
