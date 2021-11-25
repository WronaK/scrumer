package com.example.scrumer.issue.repository;

import com.example.scrumer.issue.entity.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoryJpaRepository extends JpaRepository<UserStory, Long> {
}
