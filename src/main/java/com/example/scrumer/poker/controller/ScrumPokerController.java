package com.example.scrumer.poker.controller;

import com.example.scrumer.poker.command.*;
import com.example.scrumer.poker.mapper.ScrumPokerMapper;
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
    public ScrumPoker createScrumPoker(@RequestBody CreateScrumPokerCommand command) {
        ScrumPoker scrumPoker = scrumPokerService.createScrumPoker(command);
        NotificationCommand notificationCommand = new NotificationCommand(scrumPoker.getIdScrumPoker());
        scrumPokerService.getMembers(command).forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/scrum",
                notificationCommand));
        return scrumPoker;
    }

    @PostMapping("/join")
    public ScrumPoker join(@RequestBody JoinCommand joinCommand) throws NotFoundException, IllegalAccessException {
        ScrumPoker scrumPoker = scrumPokerService.joinToScrumPoker(joinCommand);

        scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/scrum",
                ScrumPokerMapper.toEventCommand(scrumPoker, "JOIN")));
        return scrumPoker;
    }

    @PostMapping("/start/estimation")
    public void startEstimation(@RequestBody ChangeEstimationStatusCommand command) throws NotFoundException, IllegalAccessException {
        ScrumPoker scrumPoker = scrumPokerService.startEstimated(command);

        scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/scrum",
                ScrumPokerMapper.toEventCommand(scrumPoker, "START")));
    }

    @PostMapping("/stop/estimation")
    public void stopEstimation(@RequestBody ChangeEstimationStatusCommand command) throws NotFoundException, IllegalAccessException {
        StopEstimationCommand stopEstimationCommand = scrumPokerService.stopEstimated(command);
        ScrumPoker scrumPoker = scrumPokerService.getScrumPoker(command.getIdScrumPoker());

        scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/scrum",
                stopEstimationCommand));
    }

    @PostMapping("/vote")
    public void vote(@RequestBody VoteCommand command) throws NotFoundException, IllegalAccessException {
        Vote vote = scrumPokerService.voteInTask(command);
        ScrumPoker scrumPoker = scrumPokerService.getScrumPoker(command.getIdScrumPoker());

        if (vote.isNewVote()) {
            NewVoteCommand newVoteCommand = new NewVoteCommand(scrumPoker.getIdScrumPoker(), vote.getIdUser());
            scrumPoker.getMembers().forEach(member -> simpMessagingTemplate.convertAndSendToUser(member.toString(), "/queue/scrum", newVoteCommand));
        }
    }
}
