import {AfterViewInit, Component, OnInit} from '@angular/core';
import { PRODUCT_BACKLOG} from '../mock/mock-product-backlog';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddTaskToProductBacklogComponent} from "../add-task-to-product-backlog/add-task-to-product-backlog.component";
import { Task } from '../mock/task';
import {ShowTaskFromProductBacklogComponent} from "../show-task-from-product-backlog/show-task-from-product-backlog.component";

@Component({
  selector: 'app-product-backlog',
  templateUrl: './product-backlog.component.html',
  styleUrls: ['./product-backlog.component.scss']
})
export class ProductBacklogComponent implements OnInit {
  displayedColumns: string[] = ['ID', 'TITLE TASK', 'PRIORITY', 'STORY POINT(S)'];
  productBacklog = PRODUCT_BACKLOG;

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  addTask(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.dialog.open(AddTaskToProductBacklogComponent, dialogConfig);
  }

  showTask(task: Task): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: task.id,
      title: task.title,
      userStory: task.userStory,
      priority: task.priority,
      storyPoint: task.storyPoint
      };
    this.dialog.open(ShowTaskFromProductBacklogComponent, dialogConfig);
  }
}
