package com.example.scrumer.poker.controller;

import com.example.scrumer.poker.command.*;
import com.example.scrumer.poker.model.ScrumPoker;
import com.example.scrumer.poker.model.Vote;
import com.example.scrumer.poker.service.ScrumPokerService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/scrum/poker")
public class ScrumPokerController {

    private final ScrumPokerService scrumPokerService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/start")
    public ScrumPoker start(@RequestBody CreateScrumPokerCommand command) {
        ScrumPoker scrumPoker = scrumPokerService.createScrumPoker(command);
        scrumPokerService.getMembers(command).forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/scrum",
                "{\"idScrumPoker\": \"" + scrumPoker.getIdScrumPoker() + "\"}"));
        return scrumPoker;
    }

    @PostMapping("/join")
    public ScrumPoker join(@RequestBody JoinCommand joinCommand) throws NotFoundException, IllegalAccessException {
        ScrumPoker scrumPoker = scrumPokerService.joinToScrumPoker(joinCommand);

        scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/join",
                scrumPoker));
        return scrumPoker;
    }

    @PostMapping("/start/estimation")
    public void startEstimation(@RequestBody ChangeEstimationStatusCommand command) throws NotFoundException, IllegalAccessException {
        ScrumPoker scrumPoker = scrumPokerService.startEstimated(command);

        scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/start",
                scrumPoker));
    }

    @PostMapping("/stop/estimation")
    public void stopEstimation(@RequestBody ChangeEstimationStatusCommand command) throws NotFoundException, IllegalAccessException {
        ResultEstimationCommand resultEstimationCommand = scrumPokerService.stopEstimated(command);
        ScrumPoker scrumPoker = scrumPokerService.getScrumPoker(command.getIdScrumPoker());

        scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/st",
                resultEstimationCommand));
    }


    @PostMapping("/vote")
    public void vote(@RequestBody VoteCommand command) throws NotFoundException, IllegalAccessException {
        Vote vote = scrumPokerService.voteInTask(command);
        ScrumPoker scrumPoker = scrumPokerService.getScrumPoker(command.getIdScrumPoker());


        if (vote.isNewVote()) {
            scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/vote",
                    "{\"idUser\": \"" + vote.getIdUser() + "\"}"));
        }
    }
}
