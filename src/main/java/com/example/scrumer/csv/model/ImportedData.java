package com.example.scrumer.csv.model;

import com.example.scrumer.csv.command.MapValueCommand;
import com.example.scrumer.csv.command.SelectedHeader;
import com.example.scrumer.csv.command.ValueCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportedData {
    private String id;
    private String[] readHeaders;
    private List<String[]> readContent;
    private ImportOption importOption;
    private Map<String, List<String>> columns;
    private List<SelectedHeader> selectedHeaders;
    private Map<String, Values> userPreferences;


    public void addUserPreferences(List<MapValueCommand> mapValueCommands) {
        if (userPreferences == null) {
            userPreferences = new HashMap<>();
        }

        for(MapValueCommand mapValueCommand: mapValueCommands) {
            if (userPreferences.containsKey(mapValueCommand.getFields())) {
                userPreferences.get(mapValueCommand.getFields()).addValue(mapValueCommand.getValues());
            } else {
                userPreferences.put(mapValueCommand.getFields(), addValues(mapValueCommand.getValues()));
            }
        }
    }

    private Values addValues(List<ValueCommand> valueCommands) {
        Values values = new Values(new HashMap<>());

        valueCommands.forEach(valueCommand -> values.values.put(valueCommand.getMyValue(), valueCommand.getMatchValue()));
        return values;
    }
}

