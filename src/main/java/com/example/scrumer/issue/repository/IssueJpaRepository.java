package com.example.scrumer.issue.repository;

import com.example.scrumer.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueJpaRepository extends JpaRepository<Issue, Long> {
}
