import {Component, Input } from '@angular/core';
import { Task } from '../../model/task';
import {CdkDragDrop} from "@angular/cdk/drag-drop";
import {ShareService} from "../../share.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {tap} from "rxjs/operators";
import {ShowTaskFromSprintBacklogComponent} from "../show-task-from-sprint-backlog/show-task-from-sprint-backlog.component";
import {DividedIntoTasksComponent} from "../divided-into-tasks/divided-into-tasks.component";
import {TeamsDetailsService} from "../teams-details.service";
import {TaskService} from "../../task.service";

@Component({
  selector: 'app-task-states',
  templateUrl: './task-states.component.html',
  styleUrls: ['./task-states.component.scss']
})
export class TaskStatesComponent {
  @Input() title!: string;
  @Input() tasks!: Task[];
  disabled = true;

  constructor(
    private shareService: ShareService,
    private dialog: MatDialog,
    private teamDetialsService: TeamsDetailsService,
    private tasksService: TaskService) {
  }

  onDrop(event: CdkDragDrop<Task[]>) {
      this.shareService.drop(event);
  }

  displayTask(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: id,
      title: this.title
    };
    this.dialog.open(ShowTaskFromSprintBacklogComponent, dialogConfig);
  }

  divided(id: number) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: id
    };
    this.dialog.open(DividedIntoTasksComponent, dialogConfig)
      .afterClosed().pipe(
      tap(() => {
        this.teamDetialsService.loadsSprintBacklog()
      })
    ).subscribe();
  }

  changeStatus(id: number) {
    this.tasksService.changeStatusTask(id).subscribe(
      () => this.teamDetialsService.loadsSprintBacklog()
    )
  }

  addRealizeTask(id: number) {
    status = this.title==='IN-PROGRESS'? status = 'IN_PROGRESS': status = 'MERGE_REQUEST';
    this.tasksService.addRealizeTask({idTask: id, status: status}).subscribe();
  }
}
