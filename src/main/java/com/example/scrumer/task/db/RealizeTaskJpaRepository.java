package com.example.scrumer.task.db;

import com.example.scrumer.task.domain.RealizeTask;
import com.example.scrumer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealizeTaskJpaRepository extends JpaRepository<RealizeTask, Long> {

    List<RealizeTask> findRealizeTasksByUser(User user);
}
