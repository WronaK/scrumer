package com.example.scrumer.task.db;

import com.example.scrumer.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<Task, Long> {
}
