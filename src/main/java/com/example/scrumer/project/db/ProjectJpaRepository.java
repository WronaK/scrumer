package com.example.scrumer.project.db;

import com.example.scrumer.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
}
