package com.example.scrumer.poker.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ScrumPoker {
    private String idScrumPoker;
    private Long idCreator;
    private Set<Long> members;
    private Set<TaskCommand> tasks;
    private ScrumPokerStatus scrumPokerStatus;
    private Set<TeamVote> individualEstimation;
    private Long currentTask;

    public void addMember(Long member) {
        this.members.add(member);
    }

    public void addIndividualEstimation(Long idUser) {
        this.individualEstimation.add(new TeamVote(idUser, "???"));
    }

    public void removeMember(Long member) {
        this.members.remove(member);
    }
}
