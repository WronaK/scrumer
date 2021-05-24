import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TaskService} from "../../task.service";

@Component({
  selector: 'app-remove-task',
  templateUrl: './remove-task.component.html',
  styleUrls: ['./remove-task.component.scss']
})
export class RemoveTaskComponent {

  idTask: number;

  constructor(
    private dialogRef: MatDialogRef<RemoveTaskComponent>,
    private taskService: TaskService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.idTask = data.idTask;
  }

  removeTask(): void {
    this.taskService.removeTask(this.idTask).subscribe(() => this.dialogRef.close());
  }

}
