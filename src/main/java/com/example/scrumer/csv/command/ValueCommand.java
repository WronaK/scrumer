package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValueCommand {
    private String myValue;
    private String matchValue;
}
