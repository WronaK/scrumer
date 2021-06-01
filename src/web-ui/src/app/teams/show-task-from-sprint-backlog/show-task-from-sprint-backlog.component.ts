import {Component, Inject, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MAT_DIALOG_DATA,  MatDialogRef} from "@angular/material/dialog";
import {TaskService} from "../../task.service";
import { Task } from 'src/app/model/task';
import {TeamsDetailsService} from "../teams-details.service";

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

  constructor(
    private dialogRef: MatDialogRef<ShowTaskFromSprintBacklogComponent>,
    private tasksService: TaskService,
    private teamDetialsService: TeamsDetailsService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
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
    this.priorityFC.setValue(task.priority);
  }
}
