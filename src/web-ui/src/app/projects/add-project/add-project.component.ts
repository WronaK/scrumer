import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProjectsService} from "../projects.service";
import {MatDialogRef} from "@angular/material/dialog";
import {CreateProject} from "../../model/create.project";
import {JoinTeam} from "../../model/join.team";

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
  projectDetailsForm: FormGroup;

  productOwnerFC: FormControl;
  scrumMasterFC: FormControl;

  teamForm: FormGroup;
  teamNameFC: FormControl;
  accessCodeFC: FormControl;

  teams: JoinTeam[] = [];

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

    this.productOwnerFC = new FormControl('', Validators.email);
    this.scrumMasterFC = new FormControl('', Validators.email);
    this.teamNameFC = new FormControl('');
    this.accessCodeFC = new FormControl('');

    this.teamForm = new FormGroup({
      teamNameFC: this.teamNameFC,
      accessCodeFC: this.accessCodeFC
    });

    this.projectDetailsForm = new FormGroup({
      productOwnerFC: this.productOwnerFC,
      scrumMasterFC: this.scrumMasterFC,
    });

  }

  ngOnInit(): void {
  }

  save() {
    this.projectService.createProject(this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  getData(): CreateProject {
    return  {
      name: this.projectNameFC.value,
      accessCode: this.passwordFC.value,
      description: this.descriptionFC.value,
      productOwner: this.productOwnerFC.value,
      scrumMaster: this.scrumMasterFC.value,
      teams: this.teams
    }
  }

  addTeam() {
    this.teams.push({name: this.teamNameFC.value, accessCode: this.accessCodeFC.value});
    this.teamNameFC.reset();
    this.accessCodeFC.reset();
  }

}
