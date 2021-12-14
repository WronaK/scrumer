package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeaderCommand {
    private String readField;
    private String suggestedField;
    private boolean skipField;
}
