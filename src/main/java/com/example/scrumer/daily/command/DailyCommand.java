package com.example.scrumer.daily.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DailyCommand {
    private Long idTeam;
    private List<ElementDailyCommand> elements;

    public void addNewElementDailyCommand(ElementDailyCommand elementDailyCommand) {
        elements.add(elementDailyCommand);
    }
}
