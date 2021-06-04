package com.example.scrumer.task.db;

import com.example.scrumer.task.domain.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtasksJpaRepository extends JpaRepository<Subtask, Long> {
}
