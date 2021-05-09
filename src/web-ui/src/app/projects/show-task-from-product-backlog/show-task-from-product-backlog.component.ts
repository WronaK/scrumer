import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import { Task } from '../../model/task';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-show-task-from-product-backlog',
  templateUrl: './show-task-from-product-backlog.component.html',
  styleUrls: ['./show-task-from-product-backlog.component.scss']
})
export class ShowTaskFromProductBacklogComponent implements OnInit {
  taskId!: number;
  taskGroup: FormGroup;
  titleFC: FormControl;
  descriptionFC: FormControl;
  priorityFC: FormControl;
  storyPointFC: FormControl;

  constructor(
    private dialogRef: MatDialogRef<ShowTaskFromProductBacklogComponent>,
    @Inject(MAT_DIALOG_DATA) data: Task
  ) {
    this.titleFC = new FormControl('', Validators.required);
    this.descriptionFC = new FormControl('', Validators.required);
    this.priorityFC = new FormControl('', Validators.required);
    this.storyPointFC = new FormControl('', Validators.required);

    this.setValue(data);
    this.taskGroup = new FormGroup({
      titleFC: this.titleFC,
      descriptionFC: this.descriptionFC,
      priorityFC: this.priorityFC,
      storyPointFC: this.storyPointFC,
    });
  }

  ngOnInit(): void {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  save() {

  }

  setValue(data: Task): void {
    console.log(data);
    this.taskId = data.id;
    this.titleFC.setValue(data.taskDetails.title);
    this.descriptionFC.setValue(data.taskDetails.description);
    this.priorityFC.setValue(data.taskDetails.priority);
    this.storyPointFC.setValue(data.taskDetails.storyPoint);
  }
}
