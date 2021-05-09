import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProjectsService} from "../projects.service";
import {MatDialogRef} from "@angular/material/dialog";
import {CreateProject} from "../../model/create.project";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.scss']
})
export class AddProjectComponent implements OnInit {
  projectNameFC: FormControl;
  passwordFC: FormControl;
  projectForm: FormGroup;
  descriptionFC: FormControl;

  constructor(private dialogRef: MatDialogRef<AddProjectComponent>,
              private projectService: ProjectsService) {
    this.passwordFC = new FormControl('', Validators.required);
    this.projectNameFC = new FormControl('', Validators.required);
    this.descriptionFC = new FormControl('', Validators.required);
    this.projectForm = new FormGroup({
      projectNameFC: this.projectNameFC,
      passwordFC: this.passwordFC,
      descriptionFC: this.descriptionFC
    });
  }

  ngOnInit(): void {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  save() {
    this.projectService.createProject(this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  getData(): CreateProject {
    return  {
      name: this.projectNameFC.value,
      accessCode: this.passwordFC.value,
      description: this.descriptionFC.value
    }
  }

}
