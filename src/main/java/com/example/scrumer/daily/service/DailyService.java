package com.example.scrumer.daily.service;

import com.example.scrumer.daily.command.*;
import com.example.scrumer.daily.model.DailyTask;
import com.example.scrumer.daily.repository.DailyMongoRepository;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyService implements DailyUseCase {

    private final DailyMongoRepository dailyMongoRepository;
    private final TeamUseCase teamUseCase;

    @Override
    public void createTask(CreateTaskCommand command) {
        dailyMongoRepository.save(
                DailyTask.builder()
                        .userId(command.getUserId())
                        .teamId(command.getTeamId())
                        .titleTask(command.getTitleTask())
                        .typeTask(command.getTypeTask())
                        .taskId(command.getTaskId())
                        .timestamp(new Date())
                        .build()
        );
    }

    @Override
    public DailyCommand findByIdTeam(Long idTeam) throws ParseException, NotFoundException, IllegalAccessException {
        DateFormat justDay = new SimpleDateFormat("yyyyMMdd");
        Date thisMorningMidnight = justDay.parse(justDay.format(new Date()));

        Calendar c = Calendar.getInstance();
        c.setTime(thisMorningMidnight);
        c.add(Calendar.DATE, 1);
        Date tomorrowFromCalendar = c.getTime();

        Date oneDayFromCurrentDate = new Date(tomorrowFromCalendar.getTime() - Duration.ofDays(2).toMillis());

        Date todayD = new Date(tomorrowFromCalendar.getTime() - Duration.ofDays(1).toMillis());
        Team team = teamUseCase.findById(idTeam);

        DailyCommand dailyCommand = new DailyCommand(idTeam, new ArrayList<>());
        for(Member member: team.getMembersTeam()) {

            List<TaskCommand> today = new ArrayList<>();
            List<TaskCommand> yesterday = new ArrayList<>();
            List<DailyTask> dailyTasks = dailyMongoRepository.findDailyTaskByTeamIdAndUserIdAndTimestampBetween(idTeam, member.getIdUser(), oneDayFromCurrentDate, tomorrowFromCalendar);

            for(DailyTask dailyTask: dailyTasks) {
                if (dailyTask.getTimestamp().after(todayD)) {
                    today.add(new TaskCommand(dailyTask.getTitleTask(), dailyTask.getTaskId(), dailyTask.getTypeTask()));
                } else {
                    yesterday.add(new TaskCommand(dailyTask.getTitleTask(), dailyTask.getTaskId(), dailyTask.getTypeTask()));
                }
            }

            ElementDailyCommand elementDailyCommand = new ElementDailyCommand(member.getIdUser(), member.getUsername(), new TasksCommand(oneDayFromCurrentDate, yesterday), new TasksCommand(new Date(), today));
            dailyCommand.addNewElementDailyCommand(elementDailyCommand);

        }

        return dailyCommand;
    }
}
