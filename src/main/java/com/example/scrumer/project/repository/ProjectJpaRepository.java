package com.example.scrumer.project.repository;

import com.example.scrumer.project.entity.Project;
import com.example.scrumer.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {

    @Query(
            " SELECT distinct p from Project as p " +
                    " LEFT JOIN p.productOwner as productOwner " +
                    " LEFT JOIN p.teams as teams " +
                    " LEFT JOIN teams.members as members " +
                    " LEFT JOIN teams.scrumMaster as scrumMaster " +
                    " WHERE productOwner.email LIKE :email " +
                    " or members.email LIKE :email or scrumMaster.email LIKE :email "
    )
    List<Project> findProjectByUser(@Param("email") String email);

    Optional<Project> findProjectByIdAndAccessCode(Long id, String accessCode);

    @Query(
            " SELECT p from Project p " +
                    " WHERE p.projectName LIKE CONCAT('%', :name, '%') "
    )
    List<Project> findByStartedName(@Param("name") String name);
}
