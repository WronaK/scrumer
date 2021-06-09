import {Component} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {AddTaskToProductBacklogComponent} from "../add-task-to-product-backlog/add-task-to-product-backlog.component";
import {tap} from "rxjs/operators";
import {ProductBacklogService} from "../product-backlog.service";
import {RemoveTaskComponent} from "../remove-task/remove-task.component";
import {MoveTaskComponent} from "../move-task/move-task.component";

@Component({
  selector: 'app-menu-tasks',
  templateUrl: './menu-tasks.component.html',
  styleUrls: ['./menu-tasks.component.scss']
})
export class MenuTasksComponent {

  constructor(private dialog: MatDialog,
  private productBacklogService: ProductBacklogService) { }

  addTask(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: this.productBacklogService.idProject,
      request: "ADD"
    };
    this.dialog.open(AddTaskToProductBacklogComponent, dialogConfig)
      .afterClosed().pipe(
        tap(() => {
          this.productBacklogService.productBacklog();
        })
    ).subscribe();
  }

  updateTask(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: this.productBacklogService.idProject,
      idTask: this.productBacklogService.idSelectTask,
      request: "UPDATE"
    };
    this.dialog.open(AddTaskToProductBacklogComponent, dialogConfig)
      .afterClosed().pipe(
      tap(() => {
        this.productBacklogService.productBacklog();
        this.productBacklogService.selectedTask();
      })
    ).subscribe();
  }

  select() {
    return this.productBacklogService.idSelectTask != null;
  }

  deleteTask() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      idTask: this.productBacklogService.idSelectTask,
    };
    this.dialog.open(RemoveTaskComponent, dialogConfig)
      .afterClosed().pipe(
      tap(() => {
        this.productBacklogService.productBacklog();
        this.productBacklogService.setSelectTask(null);
      })
    ).subscribe();
  }

  moveTask() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.data = {
      id: this.productBacklogService.idSelectTask,
    };
    this.dialog.open(MoveTaskComponent, dialogConfig)
      .afterClosed().pipe(
      tap(() => {
        this.productBacklogService.productBacklog();
        this.productBacklogService.selectedTask();
      })
    ).subscribe();
  }
}
