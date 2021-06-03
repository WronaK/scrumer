import {Component, ElementRef, Input, OnInit, Renderer2, ViewChild} from '@angular/core';
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
export class TaskStatesComponent implements OnInit {
  @Input() title!: string;
  @Input() tasks!: Task[];
  disabled = true;

  @ViewChild('truncator') truncator!: ElementRef<HTMLElement>;
  @ViewChild('bodyText') bodyText!: ElementRef<HTMLElement>;

  constructor(
    private shareService: ShareService,
    private dialog: MatDialog,
    private teamDetialsService: TeamsDetailsService,
    private tasksService: TaskService,
    private renderer: Renderer2) {
  }

  onDrop(event: CdkDragDrop<Task[]>) {
      this.shareService.drop(event);
  }

  ngOnInit(): void {
    this.displayElement();
  }

  displayElement() {
    if (this.bodyText != null && this.truncator != null) {
      let style = window.getComputedStyle(this.bodyText.nativeElement, null);
      let viewableHeight = parseInt(style.getPropertyValue("height"), 10);

      if (this.bodyText.nativeElement.scrollHeight > viewableHeight) {
        this.renderer.setStyle(this.truncator.nativeElement, 'display', 'block');
      } else {
        this.renderer.setStyle(this.truncator.nativeElement, 'display', 'none');
      }
    }
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
