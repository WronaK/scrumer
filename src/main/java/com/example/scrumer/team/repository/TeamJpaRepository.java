package com.example.scrumer.team.repository;

import com.example.scrumer.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByIdAndAccessCode(Long id, String accessCode);

    @Query(
            " SELECT distinct t from Team as t " +
                    " LEFT JOIN t.scrumMaster as scrumMaster " +
                    " LEFT JOIN t.projects as project " +
                    " LEFT JOIN project.productOwner as productOwner " +
                    " LEFT JOIN t.members as members " +
                    " WHERE scrumMaster.email LIKE :email " +
                    " or members.email LIKE :email or productOwner.email LIKE :email "
    )
    List<Team> findByUser(@Param("email") String email);

    @Query(
            " SELECT t from Team t " +
                    " WHERE t.teamName LIKE CONCAT('%', :name, '%') "
    )
    List<Team> findByStartedName(@Param("name") String name);
}
