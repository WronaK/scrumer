package com.example.scrumer.issue.controller;

import com.example.scrumer.issue.command.CreateIssueCommand;
import com.example.scrumer.issue.command.IssueCommand;
import com.example.scrumer.issue.mapper.IssueMapper;
import com.example.scrumer.issue.service.IssueService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueController {
    private final IssueService issues;

    @GetMapping
    public List<IssueCommand> getRealizeIssue() {
        return issues.findByEmail(getUserEmail());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueCommand> getById(@PathVariable Long id) {
        return issues.findById(id).map(task -> ResponseEntity.ok(IssueMapper.toDto(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idIssue}/realize/{idUser}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addIssueToRealize(@PathVariable Long idIssue, @PathVariable Long idUser) {
        issues.addIssueToRealize(idIssue, idUser);
    }

    @PatchMapping("/{idIssue}/realize/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addIssueToRealizeMe(@PathVariable Long idIssue) {
        issues.addIssueToRealizeMe(idIssue, getUserEmail());
    }

    @PatchMapping("/{idIssue}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeStatusIssue(@PathVariable Long idIssue) {
        issues.changeStatusIssue(idIssue);
    }

    @PostMapping
    public void createIssue(@RequestBody CreateIssueCommand command) {
        issues.createIssue(command);
    }

    @PutMapping()
    public void updateIssueById(@RequestBody IssueCommand task) {
        issues.updateIssue(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        issues.deleteById(id);
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
