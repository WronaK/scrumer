package com.example.scrumer.issue.service;

import com.example.scrumer.issue.command.CreateIssueCommand;
import com.example.scrumer.issue.entity.*;
import com.example.scrumer.issue.mapper.IssueMapper;
import com.example.scrumer.issue.service.useCase.IssueUseCase;
import com.example.scrumer.issue.repository.IssueJpaRepository;
import com.example.scrumer.issue.command.IssueCommand;
import com.example.scrumer.team.repository.TeamJpaRepository;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IssueService implements IssueUseCase {
    private final IssueJpaRepository repository;
    private final TeamJpaRepository teamJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<Issue> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Issue> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateIssue(IssueCommand task) {
        repository.findById(task.getId())
                .map(savedTask -> {
                    this.updateFields(task, savedTask);
                    return repository.save(savedTask);
                });
    }

    @Override
    @Transactional
    public void createIssue(CreateIssueCommand command) {
        teamJpaRepository.findById(command.getIdTeam()).ifPresent( team -> {
                Issue issue = Issue.builder()
                        .title(command.getTitle())
                        .description(command.getDescription())
                        .priority(command.getPriority())
                        .statusIssue(command.getStatusIssue())
                        .typeIssue(command.getTypeIssue())
                        .build();

                team.addIssueToSprintBoard(repository.save(issue));
            }
        );
    }

    @Override
    public List<IssueCommand> findByEmail(String email) {
        Optional<User> user = userJpaRepository.findByEmail(email);

        return user.get().getRealizeIssues().stream().map(IssueMapper::toDto).collect(Collectors.toList());
//        return user.map(value -> realizeIssueJpaRepository.findRealizeIssueByUser(value)
//                .stream()
//                .filter(realizeIssue -> realizeIssue.getState()==realizeIssue.getIssues().getStatusIssue())
//                .map(issue -> IssueMapper.toDto(issue.getIssues())).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    @Transactional
    public void addIssueToRealize(Long idIssue, Long idUser) {
        repository.findById(idIssue)
                .ifPresent(issue -> {
                    userJpaRepository.findById(idUser).ifPresent(issue::addRealizeIssue);
                });
    }

    @Override
    @Transactional
    public void addIssueToRealizeMe(Long idIssue, String email) {
        repository.findById(idIssue)
                .ifPresent(issue -> {
                    userJpaRepository.findByEmail(email).ifPresent(issue::addRealizeIssue);
                });
    }

    @Override
    public void changeStatusIssue(Long idIssue) {
        repository.findById(idIssue).ifPresent(issue -> {
            setStatusIssue(issue);
            repository.save(issue);
        });
    }

    private void updateFields(IssueCommand task, Issue savedTask) {
        if(task.getTitle() != null) {
            savedTask.setTitle(task.getTitle());
        }

        if(task.getDescription() != null) {
            savedTask.setDescription(task.getDescription());
        }

        if(task.getPriority() != null) {
            savedTask.setPriority(task.getPriority());
        }

        if(task.getStoryPoints() != null) {
            savedTask.setStoryPoints(task.getStoryPoints());
        }

        if(task.getStatusIssue() != null) {
            savedTask.setStatusIssue(task.getStatusIssue());
        }

        if(task.getTypeIssue() != null) {
            savedTask.setTypeIssue(task.getTypeIssue());
        }
    }

    private void setStatusIssue(Issue issue) {
        switch (issue.getStatusIssue()) {
            case TO_DO -> issue.setStatusIssue(StatusIssue.IN_PROGRESS);
            case IN_PROGRESS -> issue.setStatusIssue(StatusIssue.MERGE_REQUEST);
            case MERGE_REQUEST -> issue.setStatusIssue(StatusIssue.COMPLETED);
        }
    }
}

