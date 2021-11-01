package com.example.scrumer.team.repository;

import com.example.scrumer.team.entity.Team;
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
}
