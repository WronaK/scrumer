package com.example.scrumer.issue.service.useCase;

import com.example.scrumer.issue.command.CreateIssueCommand;
import com.example.scrumer.issue.command.IssueCommand;
import com.example.scrumer.issue.entity.Issue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IssueUseCase {
    Optional<Issue> findById(Long id);

    List<Issue> findAll();

    void deleteById(Long id);

    void updateIssue(IssueCommand task);

    void createIssue(CreateIssueCommand command);

    List<IssueCommand> findByEmail(String email);

    void addIssueToRealize(Long idIssue, Long idUser);

    void changeStatusIssue(Long idIssue);
}
