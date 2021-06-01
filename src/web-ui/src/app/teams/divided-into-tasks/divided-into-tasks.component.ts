import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import { Task } from 'src/app/model/task';
import {TaskService} from "../../task.service";
import {TeamsService} from "../teams.service";
import {CreateTask} from "../../model/create.task";

@Component({
  selector: 'app-divided-into-tasks',
  templateUrl: './divided-into-tasks.component.html',
  styleUrls: ['./divided-into-tasks.component.scss']
})
export class DividedIntoTasksComponent implements OnInit {
  taskGroup: FormGroup;
  titleFC: FormControl;
  descriptionFC: FormControl;
  priorityFC: FormControl;
  pbiNameFC: FormControl;
  pbiDescriptionFC: FormControl;
  idPBI!: number;
  disabled=true;
  tasks: CreateTask[] = [];

  constructor(
    private dialogRef: MatDialogRef<DividedIntoTasksComponent>,
    private teamsService: TeamsService,
    private tasksService: TaskService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.idPBI = data.id;
    this.titleFC = new FormControl('', Validators.required);
    this.descriptionFC = new FormControl('', Validators.required);
    this.priorityFC = new FormControl('', Validators.required);
    this.taskGroup = new FormGroup({
      titleFc: this.titleFC,
      descriptionFC: this.descriptionFC,
      priorityFC: this.priorityFC,
    });

    this.pbiNameFC = new FormControl({ value: '', disabled: this.disabled });
    this.pbiDescriptionFC = new FormControl({ value: '', disabled: this.disabled });
  }

  ngOnInit(): void {
    this.tasksService.getTask(this.idPBI).subscribe(task => this.setData(task));
  }

  setData(task: Task) {
    this.pbiNameFC.setValue(task.title);
    this.pbiDescriptionFC.setValue(task.description);
  }

  addTask() {
    this.tasks.push(this.getData());
    this.taskGroup.reset();
  }

  getData() {
    return {
      title: this.titleFC.value,
      description: this.descriptionFC.value,
      priority: this.priorityFC.value
    }
  }

  save() {
    this.tasksService.addSubtasks(this.idPBI, {tasks: this.tasks})
      .subscribe(() => this.dialogRef.close());
  }
}
