package com.example.scrumer.task.repository;

import com.example.scrumer.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<Task, Long> {
}
