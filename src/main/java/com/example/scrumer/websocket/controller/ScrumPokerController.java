package com.example.scrumer.websocket.controller;

import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import com.example.scrumer.user.entity.User;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ScrumPokerController {
    private final SimpMessagingTemplate messagingTemplate;
    private final TeamUseCase teamUseCase;

    @MessageMapping("/example")
    @Transactional
    public void startScrumPoker(@Payload ScrumCommand command) throws NotFoundException {
        String id = UUID.randomUUID().toString();
        Team team = teamUseCase.findByIdTeam(command.getIdTeam()).orElseThrow(() -> new NotFoundException("Not found"));

        Set<User> teamMembers = team.getMembers();

        teamMembers.forEach(member -> messagingTemplate.convertAndSendToUser(member.getId().toString(), "/queue/scrum",
                new InitCommand(id, command.getIdTeam())));
    }

    @MessageMapping("/join")
    @Transactional
    public void joinScrumPoker(@Payload Command command) throws NotFoundException {
        System.out.println("Join user: " + command.getId());
        Team team = teamUseCase.findByIdTeam(command.getIdTeam()).orElseThrow(() -> new NotFoundException("Not found"));

        Set<User> teamMembers = team.getMembers();

        teamMembers.forEach(member -> messagingTemplate.convertAndSendToUser(member.getId().toString(), "/queue/join",
                new InfoUserCommand(member.getId())));
    }
}
