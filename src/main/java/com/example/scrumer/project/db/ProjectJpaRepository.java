package com.example.scrumer.project.db;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {

    @Query(
            " SELECT p.productBacklog from Project p " +
                    " WHERE p.id = :id_project "
    )
    List<Task> findProductBacklog(@Param("id_project") Long id);

    Project findByNameAndAccessCode(String name, String accessCode);

    @Query(
            " SELECT p.teams from Project p " +
                    " WHERE p.id = :idProject "
    )
    List<Team> findTeams(@Param("idProject") Long id);
}
