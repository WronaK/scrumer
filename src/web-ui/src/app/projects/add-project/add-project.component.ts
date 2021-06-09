import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProjectsService} from "../projects.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CreateProject} from "../../model/create.project";
import {JoinTeam} from "../../model/join.team";
import {UpdateProject} from "../../model/update.project";
import {tap} from "rxjs/operators";

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

  teamForm!: FormGroup;
  teamNameFC!: FormControl;
  accessCodeFC!: FormControl;

  teams: JoinTeam[] = [];
  request: String;

  idProject!: number;
  project!: UpdateProject;

  constructor(
    private dialogRef: MatDialogRef<AddProjectComponent>,
    private projectService: ProjectsService,
    @Inject(MAT_DIALOG_DATA) data: any
  )
  {
    this.request = data.request;
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

    if(this.request == "ADD") {
      this.teamNameFC = new FormControl('');
      this.accessCodeFC = new FormControl('');

      this.teamForm = new FormGroup({
        teamNameFC: this.teamNameFC,
        accessCodeFC: this.accessCodeFC
      });
    } else if(this.request == "UPDATE") {
      this.idProject = data.id;
      this.getProject();
    }

    this.projectDetailsForm = new FormGroup({
      productOwnerFC: this.productOwnerFC,
      scrumMasterFC: this.scrumMasterFC,
    });

  }

  ngOnInit(): void {
  }

  save() {
    if(this.request == "UPDATE") {
      this.update();
    } else if(this.request == "ADD") {
      this.create();
    }
  }

  create() {
    this.projectService.createProject(this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  update() {
    this.projectService.updateProject(this.getTaskToUpdate())
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

  setData() {
    this.projectNameFC.setValue(this.project.name);
    this.passwordFC.setValue(this.project.accessCode);
    this.descriptionFC.setValue(this.project.description);
    this.productOwnerFC.setValue(this.project.productOwner);
    this.scrumMasterFC.setValue(this.project.scrumMaster)
  }

  getTaskToUpdate() {
    return {
      id: this.idProject,
      name: this.projectNameFC.value,
      accessCode: this.passwordFC.value,
      description: this.descriptionFC.value,
      productOwner: this.productOwnerFC.value,
      scrumMaster: this.scrumMasterFC.value,
    }
  }

  getProject() {
    this.projectService.getProjectByIdUpdate(this.idProject)
      .pipe(tap(project => this.project = project))
      .subscribe(() => this.setData());
  }

}
