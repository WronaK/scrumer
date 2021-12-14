package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ResultCommand {
    private String idImportedData;
    private List<MapValueCommand> mapValue;
}
