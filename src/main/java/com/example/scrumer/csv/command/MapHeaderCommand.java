package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MapHeaderCommand {
    private String idImportedData;
    private List<SelectedHeader> selectedHeaders;
}
