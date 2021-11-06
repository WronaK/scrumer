package com.example.scrumer.project.repository;

import com.example.scrumer.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {

    @Query(
            " SELECT distinct p from Project as p " +
                    " LEFT JOIN p.creator as creator " +
                    " LEFT JOIN p.productOwner as productOwner " +
                    " LEFT JOIN p.scrumMaster as scrumMaster " +
                    " LEFT JOIN p.teams as teams " +
                    " LEFT JOIN teams.members as members " +
                    " WHERE creator.email = :email " +
                    " or productOwner.email = :email" +
                    " or scrumMaster.email = :email " +
                    " or members.email = :email "
    )
    List<Project> findProjectByUser(@Param("email") String email);
}
