import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProjectsService} from "../projects.service";

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
  id: number;

  constructor(private dialogRef: MatDialogRef<AddTaskToProductBacklogComponent>,
              private projectService: ProjectsService,
              @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.titleFC = new FormControl('', Validators.required);
    this.descriptionFC = new FormControl('', Validators.required);
    this.priorityFC = new FormControl('', Validators.required);
    this.taskGroup = new FormGroup({
      titleFc: this.titleFC,
      descriptionFC: this.descriptionFC,
      priorityFC: this.priorityFC
    });
    this.id = data.id;
  }

  ngOnInit(): void {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  save() {
    this.projectService.addTaskToProductBacklog(this.id, this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  getData() {
    return {
      title: this.titleFC.value,
      description: this.descriptionFC.value,
      priority: this.priorityFC.value
    }
  }
}
