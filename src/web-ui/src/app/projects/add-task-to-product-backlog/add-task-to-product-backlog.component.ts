import {Component, Inject } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProjectsService} from "../projects.service";
import {Task} from 'src/app/model/task/task';
import {tap} from "rxjs/operators";
import {TaskService} from "../../task.service";
import {PriorityStatus} from "../../model/task/priority.status";

@Component({
  selector: 'app-add-task-to-product-backlog',
  templateUrl: './add-task-to-product-backlog.component.html',
  styleUrls: ['./add-task-to-product-backlog.component.scss']
})
export class AddTaskToProductBacklogComponent {
  priority = PriorityStatus;
  keys: any[] = [];
  taskGroup: FormGroup;
  titleFC: FormControl;
  descriptionFC: FormControl;
  priorityFC: FormControl;
  storyPointFC: FormControl;
  id: number;
  request: string;
  idTask!: number;
  task!: Task;

  constructor(private dialogRef: MatDialogRef<AddTaskToProductBacklogComponent>,
              private projectService: ProjectsService,
              private tasksService: TaskService,
              @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.keys = Object.keys(this.priority).filter(f => !isNaN(Number(f)));
    this.titleFC = new FormControl('', Validators.required);
    this.descriptionFC = new FormControl('', Validators.required);
    this.priorityFC = new FormControl('', Validators.required);
    this.storyPointFC = new FormControl();
    this.taskGroup = new FormGroup({
      titleFc: this.titleFC,
      descriptionFC: this.descriptionFC,
      priorityFC: this.priorityFC,
      storyPointFC: this.storyPointFC
    });
    this.request = data.request;
    this.id = data.id;
    if (this.request === "UPDATE") {
      this.idTask = data.idTask;
      this.getTask();
    }

  }

  save() {
    if (this.request === "UPDATE") {
      this.update();
    } else if (this.request === "ADD") {
      this.create();
    }
  }

  create() {
    this.projectService.addTaskToProductBacklog(this.id, this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  update() {
    this.tasksService.updateTask(this.getTaskToUpdate())
      .subscribe(() => this.dialogRef.close());

  }

  getData() {
    return {
      title: this.titleFC.value,
      description: this.descriptionFC.value,
      priority: this.priorityFC.value
    }
  }

  getTaskToUpdate() {
    return {
      id: this.idTask,
      title: this.titleFC.value,
      description: this.descriptionFC.value,
      priority: this.priorityFC.value,
      storyPoints: this.storyPointFC.value,
      status: ''
    }
  }

  setData() {
    this.titleFC.setValue(this.task.title);
    this.descriptionFC.setValue(this.task.description);
    this.priorityFC.setValue(<keyof typeof PriorityStatus>this.task.priority);
    this.storyPointFC.setValue(this.task.storyPoints);
  }

  getTask() {
    this.tasksService.getTask(this.idTask).pipe(
      tap(task =>
        this.task = task
      )).subscribe(() => this.setData());
  }
}
