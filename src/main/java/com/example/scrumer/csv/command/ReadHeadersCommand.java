package com.example.scrumer.csv.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ReadHeadersCommand {
    private String idImportedData;
    private List<HeaderCommand> headers;
    private List<String> availableFields;
}
