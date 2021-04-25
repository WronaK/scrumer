import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-task-to-product-backlog',
  templateUrl: './add-task-to-product-backlog.component.html',
  styleUrls: ['./add-task-to-product-backlog.component.scss']
})
export class AddTaskToProductBacklogComponent implements OnInit {
  taskGroup: FormGroup;
  titleFC: FormControl;
  descriptionFC: FormControl;
  priorityFC: FormControl;

  constructor(private dialogRef: MatDialogRef<AddTaskToProductBacklogComponent>) {
    this.titleFC = new FormControl('', Validators.required);
    this.descriptionFC = new FormControl('', Validators.required);
    this.priorityFC = new FormControl('', Validators.required);
    this.taskGroup = new FormGroup({
      titleFc: this.titleFC,
      descriptionFC: this.descriptionFC,
      priorityFC: this.priorityFC
    });
  }

  ngOnInit(): void {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  save() {
    //TODO
  }

  getData() {
    return {
      title: this.titleFC.value,
      description: this.descriptionFC.value,
      priority: this.priorityFC.value
    }
  }
}
