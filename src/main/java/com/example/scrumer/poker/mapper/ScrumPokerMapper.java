package com.example.scrumer.poker.mapper;

import com.example.scrumer.poker.command.EventCommand;
import com.example.scrumer.poker.model.ScrumPoker;
import org.springframework.stereotype.Component;

@Component
public class ScrumPokerMapper {

    public static EventCommand toEventCommand(ScrumPoker scrumPoker, String method) {
        return EventCommand.builder()
                .method(method)
                .idScrumPoker(scrumPoker.getIdScrumPoker())
                .idCreator(scrumPoker.getIdCreator())
                .members(scrumPoker.getMembers())
                .tasks(scrumPoker.getTasks())
                .scrumPokerStatus(scrumPoker.getScrumPokerStatus())
                .individualEstimation(scrumPoker.getIndividualEstimation())
                .currentTask(scrumPoker.getCurrentTask()).build();
    }
}
