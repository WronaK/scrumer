import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddTaskToProductBacklogComponent} from "../add-task-to-product-backlog/add-task-to-product-backlog.component";
import { Task } from '../../model/task';
import {ShowTaskFromProductBacklogComponent} from "../show-task-from-product-backlog/show-task-from-product-backlog.component";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {ProjectsService} from "../projects.service";
import {switchMap, tap} from "rxjs/operators";

@Component({
  selector: 'app-product-backlog',
  templateUrl: './product-backlog.component.html',
  styleUrls: ['./product-backlog.component.scss']
})
export class ProductBacklogComponent implements OnInit {
  displayedColumns: string[] = ['ID', 'TITLE TASK', 'PRIORITY', 'STORY POINT(S)'];
  productBacklog: Task[] = [];
  id!: number;

  constructor(
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private projectService: ProjectsService) {
    route.params.subscribe(params => this.id = parseInt(params['id']));
    console.log(this.id);
  }

  ngOnInit(): void {
    this.getProductBacklog().subscribe();
  }

  addTask(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: this.id
    };
    this.dialog.open(AddTaskToProductBacklogComponent, dialogConfig)
      .afterClosed()
      .pipe(
        switchMap(() => this.getProductBacklog())
      ).subscribe();  }

  showTask(task: Task): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: task.id,
      taskDetails: {
        title: task.taskDetails.title,
        description: task.taskDetails.description,
        priority: task.taskDetails.priority,
        storyPoint: task.taskDetails.storyPoint
      }
      };
    this.dialog.open(ShowTaskFromProductBacklogComponent, dialogConfig);
  }

  getProductBacklog(): Observable<Task[]> {
    return this.projectService.getTasksToProductBacklog(this.id)
      .pipe(tap(productBacklog =>  {this.productBacklog=productBacklog;
      console.log(productBacklog)}
      ));
  }
}
