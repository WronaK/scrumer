package com.example.scrumer.security;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.domain.User;
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

    private boolean isScrumMaster(Set<Project> projects, String email) {
        for (Project project: projects) {
            if(project.getScrumMaster() != null && email.equals(project.getScrumMaster().getEmail())) {
                return true;
            }
        }
        return false;
    }

    public void validateModifyTeamPermission(Optional<Team> team, String email) throws IllegalAccessException, NotFoundException {
        if(team.isPresent()) {
            Team myTeam = team.get();
            if(email.equals(myTeam.getCreator().getEmail())) {
                return;
            }

            if(this.isScrumMaster(myTeam.getProjects(), email)) {
                return;
            }

            throw new IllegalAccessException("Illegal team permission exception");
        }
        throw new NotFoundException("Not found team.");
    }

    public void validateTeamPermission(Optional<Team> team, String email) throws IllegalAccessException, NotFoundException {
        if(team.isPresent()) {
            Team myTeam = team.get();
            if(email.equals(myTeam.getCreator().getEmail())) {
                return;
            }

            if(this.isScrumMaster(myTeam.getProjects(), email)) {
                return;
            }

            if(this.isMemberTeam(myTeam.getMembers(), email)) {
                return;
            }

            throw new IllegalAccessException("Illegal team permission exception");
        }
        throw new NotFoundException("Not found team.");
    }

    public void validateModifyProjectPermission(Optional<Project> project, String email) throws IllegalAccessException, NotFoundException {
        if(project.isPresent()) {
            Project myProject = project.get();
            if (myProject.getScrumMaster() != null && email.equals(myProject.getScrumMaster().getEmail())) {
                return;
            }

            if (email.equals(myProject.getCreator().getEmail())) {
                return;
            }

            throw new IllegalAccessException("Illegal project permission exception");
        }

        throw new NotFoundException("Not found project.");
    }

    public void validateProjectPermission(Optional<Project> project, String email) throws IllegalAccessException, NotFoundException {
        if(project.isPresent()) {
            Project myProject = project.get();
            if (myProject.getProductOwner() != null &&
                    email.equals(myProject.getProductOwner().getEmail())) {
                return;
            }

            if (myProject.getScrumMaster() != null && email.equals(myProject.getScrumMaster().getEmail())) {
                return;
            }

            if (email.equals(myProject.getCreator().getEmail())) {
                return;
            }

            for (Team team : myProject.getTeams()) {
                if (this.isMemberTeam(team.getMembers(), email)) {
                    return;
                }
            }
            throw new IllegalAccessException("Illegal project permission exception");

        }
        throw new NotFoundException("Not found project.");
    }
}
