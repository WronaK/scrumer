package com.example.scrumer.task.db;

import com.example.scrumer.task.domain.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDetailsJpaRepository extends JpaRepository<TaskDetails, Long> {
}
