package com.example.scrumer.team.command;

import com.example.scrumer.task.mapper.TaskMapper;
import com.example.scrumer.task.entity.Subtask;
import com.example.scrumer.task.entity.Task;
import com.example.scrumer.task.command.TaskCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SprintBacklogCommand {
    private List<TaskCommand> tasksPBI = new ArrayList<>();
    private List<TaskCommand> tasksTasks = new ArrayList<>();
    private List<TaskCommand> tasksInProgress = new ArrayList<>();
    private List<TaskCommand> tasksMergeRequest = new ArrayList<>();
    private List<TaskCommand> tasksDone = new ArrayList<>();

    public SprintBacklogCommand(List<Task> sprintBacklog) {
        this.sort(sprintBacklog);
    }

    private void sort(List<Task> sprintBacklog) {
        for(Task task: sprintBacklog) {
            tasksPBI.add(TaskMapper.toDto(task));
            for(Subtask subtask: task.getSubtasks()) {
                switch (subtask.getStatusTask()) {
                    case NEW_TASK:
                        tasksTasks.add(TaskMapper.toDto(subtask));
                        break;
                    case IN_PROGRESS:
                        tasksInProgress.add(TaskMapper.toDto(subtask));
                        break;
                    case MERGE_REQUEST:
                        tasksMergeRequest.add(TaskMapper.toDto(subtask));
                        break;
                    case DONE:
                        tasksDone.add(TaskMapper.toDto(subtask));
                        break;
                }
            }
        }
    }
}
