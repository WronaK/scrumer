package com.example.scrumer.poker.service;

import com.example.scrumer.issue.entity.Issue;
import com.example.scrumer.issue.entity.TypeTask;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.poker.command.*;
import com.example.scrumer.poker.model.ScrumPoker;
import com.example.scrumer.poker.model.ScrumPokerStatus;
import com.example.scrumer.poker.model.Vote;
import com.example.scrumer.poker.storage.ScrumPokerStorage;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ScrumPokerService {
    private final TeamUseCase teamUseCase;

    public ScrumPoker createScrumPoker(CreateScrumPokerCommand createScrumPoker) {
        ScrumPoker scrumPoker = ScrumPoker.builder()
                .idScrumPoker(UUID.randomUUID().toString())
                .idCreator(createScrumPoker.getIdCreator())
                .scrumPokerStatus(ScrumPokerStatus.NEW)
                .members(new HashSet<>())
                .individualEstimation(new HashSet<>())
                .tasks(getTasks(createScrumPoker))
                .build();

        scrumPoker.addMember(createScrumPoker.getIdCreator());
        scrumPoker.addIndividualEstimation(createScrumPoker.getIdCreator());
        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);
        return scrumPoker;
    }

    public Set<Long> getMembers(CreateScrumPokerCommand createScrumPokerCommand) {
        Set<Long> membersSet = new HashSet<>();

        membersSet.addAll(createScrumPokerCommand.getInvitedMembers());


        for (Long id: createScrumPokerCommand.getInvitedTeams()) {
            teamUseCase.findTeamById(id).ifPresent(team -> {
                membersSet.add(team.getScrumMaster().getId());
                team.getMembers().forEach(member -> membersSet.add(member.getId()));
            });
        }

        membersSet.remove(createScrumPokerCommand.getIdCreator());

        return membersSet;
    }

    private Set<TaskCommand> getTasks(CreateScrumPokerCommand createScrumPokerCommand) {
        Set<TaskCommand> tasksSet = new HashSet<>();

        for(Long id: createScrumPokerCommand.getInvitedTeams()) {
            teamUseCase.findTeamById(id).ifPresent(team -> {

                for(Issue issue: team.getSprintBoard()) {

                    if (issue.getStoryPoints() == null) {
                        tasksSet.add(new TaskCommand(issue.getId(), TypeTask.ISSUE));
                    }
                }

                for(UserStory userStory: team.getSprintBacklog()) {
                    if (userStory.getStoryPoints() == null) {
                        tasksSet.add(new TaskCommand(userStory.getId(), TypeTask.USER_STORY));
                    }
                }
            });
        }
        return tasksSet;
    }

    public ScrumPoker joinToScrumPoker(JoinCommand joinCommand) throws NotFoundException, IllegalAccessException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(joinCommand.getIdScrumPoker())) {
            throw new NotFoundException("Scrum Poker with id: " + joinCommand.getIdScrumPoker() + " doesn't exist.");
        }

        ScrumPoker scrumPoker = ScrumPokerStorage.getInstance().getStorage().get(joinCommand.getIdScrumPoker());

        if (scrumPoker.getScrumPokerStatus() == ScrumPokerStatus.ENDED) {
            throw new IllegalAccessException("Scrum Poker with id: " + joinCommand.getIdScrumPoker() + " is FINISHED!");
        }

        scrumPoker.addMember(joinCommand.getIdUser());
        scrumPoker.addIndividualEstimation(joinCommand.getIdUser());
        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);

        return scrumPoker;
    }

    public ScrumPoker startEstimated(ChangeEstimationStatusCommand command) throws NotFoundException, IllegalAccessException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(command.getIdScrumPoker())) {
            throw new NotFoundException("Scrum Poker with id: " + command.getIdScrumPoker() + " doesn't exist.");
        }

        ScrumPoker scrumPoker = ScrumPokerStorage.getInstance().getStorage().get(command.getIdScrumPoker());

        if (scrumPoker.getScrumPokerStatus() != ScrumPokerStatus.NEW) {
            throw new IllegalAccessException("Estimation cannot be started!");
        }

        scrumPoker.setScrumPokerStatus(ScrumPokerStatus.IN_PROCESS_ESTIMATION);
        scrumPoker.setCurrentTask(command.getIdTask());
        scrumPoker.getIndividualEstimation().forEach(estimation -> estimation.setEstimation("???"));
        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);

        return scrumPoker;
    }

    public ResultEstimationCommand stopEstimated(ChangeEstimationStatusCommand command) throws NotFoundException, IllegalAccessException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(command.getIdScrumPoker())) {
            throw new NotFoundException("Scrum Poker with id: " + command.getIdScrumPoker() + " doesn't exist.");
        }

        ScrumPoker scrumPoker = ScrumPokerStorage.getInstance().getStorage().get(command.getIdScrumPoker());

        if (scrumPoker.getScrumPokerStatus() != ScrumPokerStatus.IN_PROCESS_ESTIMATION) {
            throw new IllegalAccessException("Estimation cannot be stoped!");
        }

        scrumPoker.setScrumPokerStatus(ScrumPokerStatus.NEW);
        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);

        return ResultEstimationCommand.builder()
                .idScrumPoker(scrumPoker.getIdScrumPoker())
                .resultEstimation(calculateResult(scrumPoker.getIndividualEstimation()))
                .idTask(scrumPoker.getCurrentTask())
                .estimation(scrumPoker.getIndividualEstimation())
                .build();
    }

    private String calculateResult(Set<TeamVote> teamVotes) {
        List<Integer> votes = new ArrayList<>();

        for (TeamVote vote: teamVotes) {
            if (vote.getEstimation().equals("?")) {
                return "I can't estimate the value";
            }

            votes.add(Integer.valueOf(vote.getEstimation()));
        }

        Collections.sort(votes);

        int middle = teamVotes.size()/2;
        return String.valueOf((teamVotes.size() % 2) == 1 ? votes.get(middle) : Integer.valueOf((votes.get(middle - 1) + votes.get(middle)) / 2));
    }

    public ScrumPoker leaveScrumPoker(Long idUser, String idScrumPoker) throws NotFoundException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(idScrumPoker)) {
            throw new NotFoundException("Scrum Poker with id: " + idScrumPoker + " doesn't exist.");
        }

        ScrumPoker scrumPoker = ScrumPokerStorage.getInstance().getStorage().get(idScrumPoker);

        scrumPoker.removeMember(idUser);
        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);

        return scrumPoker;
    }

    public ScrumPoker closeScrumPoker(String idScrumPoker) throws NotFoundException, IllegalAccessException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(idScrumPoker)) {
            throw new NotFoundException("Scrum Poker with id: " + idScrumPoker + " doesn't exist.");
        }

        ScrumPoker scrumPoker = ScrumPokerStorage.getInstance().getStorage().get(idScrumPoker);

        if (scrumPoker.getScrumPokerStatus() != ScrumPokerStatus.NEW) {
            throw new IllegalAccessException("Estimation cannot be finished!");
        }

        scrumPoker.setScrumPokerStatus(ScrumPokerStatus.ENDED);
        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);

        return scrumPoker;
    }

    public Vote voteInTask(VoteCommand command) throws NotFoundException, IllegalAccessException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(command.getIdScrumPoker())) {
            throw new NotFoundException("Scrum Poker with id: " + command.getIdScrumPoker() + " doesn't exist.");
        }

        ScrumPoker scrumPoker = ScrumPokerStorage.getInstance().getStorage().get(command.getIdScrumPoker());

        if (scrumPoker.getScrumPokerStatus() != ScrumPokerStatus.IN_PROCESS_ESTIMATION) {
            throw new IllegalAccessException("Estimation cannot be voted!");
        }
        Vote vote = new Vote();
        if (Objects.equals(scrumPoker.getCurrentTask(), command.getIdTask())) {


            for (TeamVote teamVote: scrumPoker.getIndividualEstimation()) {
                if (Objects.equals(teamVote.getIdUser(), command.getIdUser())) {
                    vote.setNewVote(Objects.equals(teamVote.getEstimation(), "???"));
                    teamVote.setEstimation(command.getEstimation());
                    vote.setIdUser(command.getIdUser());
                }
            }
        }

        ScrumPokerStorage.getInstance().setScrumPoker(scrumPoker);

        return vote;
    }

    public ScrumPoker getScrumPoker(String idScrumPoker) throws NotFoundException {
        if (!ScrumPokerStorage.getInstance().getStorage().containsKey(idScrumPoker)) {
            throw new NotFoundException("Scrum Poker with id: " + idScrumPoker + " doesn't exist.");
        }

        return ScrumPokerStorage.getInstance().getStorage().get(idScrumPoker);
    }
}
