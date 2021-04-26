package com.example.scrumer.team.db;

import com.example.scrumer.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    Team findByNameAndAccessCode(String name, String accessCode);
}
