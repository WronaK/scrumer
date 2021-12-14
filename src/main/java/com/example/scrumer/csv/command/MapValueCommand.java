package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MapValueCommand {
    private String fields;
    private List<ValueCommand> values;
}
