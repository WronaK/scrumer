package com.example.scrumer.csv.controller;

import com.example.scrumer.csv.command.MapHeaderCommand;
import com.example.scrumer.csv.command.MyValue;
import com.example.scrumer.csv.command.ReadHeadersCommand;
import com.example.scrumer.csv.command.ResultCommand;
import com.example.scrumer.csv.model.ImportOption;
import com.example.scrumer.csv.service.ImportCsvStrategy;
import com.example.scrumer.csv.service.ImportUserStoryService;
import com.example.scrumer.csv.service.IssueImportService;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.issue.service.useCase.IssueUseCase;
import com.example.scrumer.issue.service.useCase.UserStoryUseCase;
import com.example.scrumer.project.service.useCase.ProjectUseCase;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/csv")
@RestController
public class CsvController {
    private static ImportCsvStrategy importCsvStrategy;
    private final IssueUseCase issueUseCase;
    private final UserStoryUseCase userStoryUseCase;

    @PostMapping("/{option}/import")
    public ResponseEntity<ReadHeadersCommand> importCsv(@PathVariable ImportOption option, @RequestParam("csv") MultipartFile file) {

        if (option == ImportOption.USER_STORY_TO_PRODUCT_BACKLOG) {
            importCsvStrategy = new ImportUserStoryService(userStoryUseCase);
        } else if(option == ImportOption.ISSUE_TO_SPRINT_BOARD) {
            importCsvStrategy = new IssueImportService(issueUseCase);
        }

        return ResponseEntity.ok(importCsvStrategy.importCsvFile(option, file));
    }

    @PostMapping("/selected/fields")
    public MyValue saveSelectedFields(@RequestBody MapHeaderCommand selectedHeader) {
        return importCsvStrategy.selectedFields(selectedHeader);
    }

    @PostMapping("/match")
    public void matchFields(@RequestBody ResultCommand resultCommand) {
        importCsvStrategy.matchFields(resultCommand);
    }

    @PostMapping("/{idData}/select/{id}")
    public void select(@PathVariable String idData, @PathVariable Long id) {
        importCsvStrategy.create(idData, id);
    }
}
