package com.example.scrumer.team.db;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByNameAndAccessCode(String name, String accessCode);

    @Query(
            " SELECT t from Team t JOIN t.members m " +
                    " WHERE " +
                    " m.email LIKE :email "
    )
    List<Team> findByUser(@Param("email") String email);

    @Query(
            " SELECT t.sprintBoard from Team t " +
                    " WHERE t.id = :id_team "
    )
    List<Task> getSprintBacklog(@Param("id_team") Long id);

    @Query(
            " select t.members from Team t " +
                    " where t.id = :idTeam"
    )
    List<User> findMembers(@Param("idTeam") Long id);

    @Query(
            " select t.projects from Team t " +
                    " where t.id = :idTeam"
    )
    List<Project> findProjects(@Param("idTeam") Long id);
}
