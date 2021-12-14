package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SelectedHeader {
    private String readField;
    private String suggestedField;
}
