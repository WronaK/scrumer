package com.example.scrumer.daily.service;

import com.example.scrumer.daily.command.*;
import com.example.scrumer.daily.model.Daily;
import com.example.scrumer.daily.model.DailyMember;
import com.example.scrumer.daily.model.DailyTask;
import com.example.scrumer.daily.repository.DailyMemberMongoRepository;
import com.example.scrumer.daily.repository.DailyMongoRepository;
import com.example.scrumer.daily.repository.DailyTaskMongoRepository;
import com.example.scrumer.daily.service.useCase.DailyUseCase;
import com.example.scrumer.team.entity.Member;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DailyService implements DailyUseCase {

    private final DailyMongoRepository dailyMongoRepository;
    private final DailyMemberMongoRepository dailyMemberMongoRepository;
    private final DailyTaskMongoRepository dailyTaskMongoRepository;
    private final TeamUseCase teamUseCase;

    @Override
    public void createTask(CreateTaskCommand command) throws ParseException {
        DateFormat justDay = new SimpleDateFormat("yyyyMMdd");
        Date thisMorningMidnight = justDay.parse(justDay.format(new Date()));
        Date thisTomorrowMidnight = new Date(thisMorningMidnight.getTime() + Duration.ofDays(1).toMillis());

        DailyTask dailyTask = dailyTaskMongoRepository.save(
                DailyTask.builder()
                        .titleTask(command.getTitleTask())
                        .typeTask(command.getTypeTask())
                        .taskId(command.getTaskId())
                        .timestamp(new Date())
                        .build()
        );

        Optional<Daily> daily = dailyMongoRepository.findDailyByIdTeamAndDateBetween(command.getTeamId(), thisMorningMidnight, thisTomorrowMidnight);
        Daily savedDaily = null;
        if (daily.isPresent()) {
            savedDaily = daily.get();

            Optional<DailyMember> memberDaily = savedDaily.getDailyMembers().stream().filter(dailyMember -> dailyMember.getIdUser().equals(command.getUserId())).findFirst();


            if(memberDaily.isPresent()) {
                memberDaily.get().addDailyTask(dailyTask);
                dailyMemberMongoRepository.save(memberDaily.get());
            } else {
                DailyMember dailyMember = DailyMember.builder().idUser(command.getUserId()).tasks(List.of(dailyTask)).build();
                dailyMemberMongoRepository.save(dailyMember);
            }

        } else {
            DailyMember dailyMember = DailyMember.builder().idUser(command.getUserId()).tasks(List.of(dailyTask)).build();
            DailyMember savedDailyMember = dailyMemberMongoRepository.save(dailyMember);

            savedDaily = dailyMongoRepository.save(Daily.builder().date(new Date()).dailyMembers(List.of(savedDailyMember)).idTeam(command.getTeamId()).build());
        }

        dailyMongoRepository.save(savedDaily);

    }

    @Override
    public DailyCommand findByIdTeam(Long idTeam) throws ParseException, NotFoundException, IllegalAccessException {

        DateFormat justDay = new SimpleDateFormat("yyyyMMdd");
        Date thisMorningMidnight = justDay.parse(justDay.format(new Date()));
        Date thisTomorrowMidnight = new Date(thisMorningMidnight.getTime() + Duration.ofDays(1).toMillis());
        Date thisYesterdayMidnight = new Date(thisMorningMidnight.getTime() - Duration.ofDays(1).toMillis());


        Team team = teamUseCase.findById(idTeam);

        Optional<Daily> today = dailyMongoRepository.findDailyByIdTeamAndDateBetween(idTeam, thisMorningMidnight, thisTomorrowMidnight);
        Optional<Daily> yesterday = dailyMongoRepository.findDailyByIdTeamAndDateBetween(idTeam, thisYesterdayMidnight, thisMorningMidnight);

        DailyCommand dailyCommand = DailyCommand.builder().idTeam(idTeam).elements(new ArrayList<>()).build();

        for(Member member: team.getMembersTeam()) {

            List<TaskCommand> todayTaskCommands = new ArrayList<>();
            List<TaskCommand> yesterdayTasksCommands = new ArrayList<>();

            if (today.isPresent()) {
                for (DailyMember dailyMember : today.get().getDailyMembers()) {
                    if (dailyMember.getIdUser().equals(member.getIdUser())) {

                        for(DailyTask dailyTask: dailyMember.getTasks()) {
                            todayTaskCommands.add(new TaskCommand(dailyTask.getTitleTask(), dailyTask.getTaskId(), dailyTask.getTypeTask()));
                        }
                    }
                }
            }

            if (yesterday.isPresent()) {
                for (DailyMember dailyMember : yesterday.get().getDailyMembers()) {
                    if (dailyMember.getIdUser().equals(member.getIdUser())) {

                        for(DailyTask dailyTask: dailyMember.getTasks()) {
                            yesterdayTasksCommands.add(new TaskCommand(dailyTask.getTitleTask(), dailyTask.getTaskId(), dailyTask.getTypeTask()));
                        }
                    }
                }
            }

            ElementDailyCommand elementDailyCommand = new ElementDailyCommand(member.getIdUser(), member.getUsername(), new TasksCommand(thisYesterdayMidnight, yesterdayTasksCommands), new TasksCommand(new Date(), todayTaskCommands));
            dailyCommand.addNewElementDailyCommand(elementDailyCommand);

        }

        return dailyCommand;
    }
}
