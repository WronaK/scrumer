import {Component, Inject, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MAT_DIALOG_DATA,  MatDialogRef} from "@angular/material/dialog";
import {TaskService} from "../../task.service";
import { Task } from 'src/app/model/task/task';
import {TeamsDetailsService} from "../teams-details.service";
import {PriorityStatus} from "../../model/task/priority.status";

@Component({
  selector: 'app-show-task-from-sprint-backlog',
  templateUrl: './show-task-from-sprint-backlog.component.html',
  styleUrls: ['./show-task-from-sprint-backlog.component.scss']
})
export class ShowTaskFromSprintBacklogComponent implements OnInit {
  taskId!: number;
  titleFC: FormControl;
  descriptionFC: FormControl;
  priorityFC: FormControl;
  storyPointFC: FormControl;
  disabled = true;
  title!: string;
  keys: any[] = [];
  priority = PriorityStatus;

  constructor(
    private dialogRef: MatDialogRef<ShowTaskFromSprintBacklogComponent>,
    private tasksService: TaskService,
    private teamsDetailsService: TeamsDetailsService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.keys = Object.keys(this.priority).filter(f => !isNaN(Number(f)));
    this.taskId = data.id;
    this.title = data.title;
    this.titleFC = new FormControl({ value: '', disabled: this.disabled });
    this.descriptionFC = new FormControl({ value: '', disabled: this.disabled });
    this.priorityFC = new FormControl({ value: '', disabled: this.disabled });
    this.storyPointFC = new FormControl({ value: '', disabled: this.disabled });
  }

  ngOnInit(): void {

    if(this.title == 'PBI') {
      this.tasksService.getTask(this.taskId).subscribe(
        task => this.setData(task)
      )
    } else {
      this.tasksService.getSubtask(this.taskId).subscribe(
        task => this.setData(task)
      )
    }
  }

  setData(task: Task) {
    this.titleFC.setValue(task.title);
    this.descriptionFC.setValue(task.description);
    this.storyPointFC.setValue(task.storyPoints);
    this.priorityFC.setValue(<keyof typeof PriorityStatus>task.priority);
  }
}
