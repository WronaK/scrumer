package com.example.scrumer.task.repository;

import com.example.scrumer.task.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskJpaRepository extends JpaRepository<Subtask, Long> {
}
