import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {JoinTeam} from "../../model/team/join.team";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ProjectsService} from "../projects.service";

@Component({
  selector: 'app-add-teams',
  templateUrl: './add-teams.component.html',
  styleUrls: ['./add-teams.component.scss']
})
export class AddTeamsComponent implements OnInit {
  idProject!: number;
  teamForm!: FormGroup;
  teamNameFC!: FormControl;
  accessCodeFC!: FormControl;
  teams: JoinTeam[] = [];

  constructor(
    private dialogRef: MatDialogRef<AddTeamsComponent>,
    private projectService: ProjectsService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.idProject = data.id;
    this.teamNameFC = new FormControl('', Validators.required);
    this.accessCodeFC = new FormControl('', Validators.required);

    this.teamForm = new FormGroup({
      teamNameFC: this.teamNameFC,
      accessCodeFC: this.accessCodeFC
    });
  }

  ngOnInit(): void {
  }

  addTeam(): void {
    this.teams.push({name: this.teamNameFC.value, accessCode: this.accessCodeFC.value});
    this.teamNameFC.reset();
    this.accessCodeFC.reset();
  }

  save(): void {
    this.projectService.joinTeamsToProject(this.idProject, {
      teams: this.teams
    }).subscribe(() => this.dialogRef.close());
  }
}
