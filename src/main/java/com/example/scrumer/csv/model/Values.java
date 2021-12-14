package com.example.scrumer.csv.model;

import com.example.scrumer.csv.command.ValueCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Values {
    Map<String, String> values;


    public void addValue(List<ValueCommand> valueCommands) {
        if (values == null) {
            values = new HashMap<>();
        }

        valueCommands.forEach(valueCommand -> values.put(valueCommand.getMyValue(), valueCommand.getMatchValue()));
    }
}
