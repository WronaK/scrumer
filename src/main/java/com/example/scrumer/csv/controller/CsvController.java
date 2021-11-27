package com.example.scrumer.csv.controller;

import com.example.scrumer.csv.command.ImportResult;
import com.example.scrumer.csv.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RequiredArgsConstructor
@RequestMapping("/api/csv")
@Controller
public class CsvController {
    private final CsvService csvService;

    @PostMapping("/{id}/import")
    public ResponseEntity<ImportResult> uploadCsv(@PathVariable Long id, @RequestParam("csv") MultipartFile file) {
//        return new ImportResult(csvService.readData(file));
        return ResponseEntity.ok(new ImportResult(csvService.readData(file), csvService.getFields()));
    }
}
