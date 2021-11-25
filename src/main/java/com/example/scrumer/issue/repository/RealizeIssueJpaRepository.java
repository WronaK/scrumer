package com.example.scrumer.issue.repository;

import com.example.scrumer.issue.entity.RealizeIssue;
import com.example.scrumer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealizeIssueJpaRepository extends JpaRepository<RealizeIssue, Long> {

    List<RealizeIssue> findRealizeIssueByUser(User user);
}
