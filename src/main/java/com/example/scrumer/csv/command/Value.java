package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class Value {
    private String fields;
    private Set<String> uniqueValues;
    private List<String> availableValue;
}
