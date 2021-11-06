package com.example.scrumer.task.repository;

import com.example.scrumer.task.entity.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDetailsJpaRepository extends JpaRepository<TaskDetails, Long> {
}
