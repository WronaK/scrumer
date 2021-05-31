import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TeamsService} from "../teams.service";

@Component({
  selector: 'app-join-project',
  templateUrl: './join-project.component.html',
  styleUrls: ['./join-project.component.scss']
})
export class JoinProjectComponent {
  projectForm: FormGroup;
  projectNameFC: FormControl;
  accessCodeFC: FormControl;
  idTeam!: number;

  constructor(
    private dialogRef: MatDialogRef<JoinProjectComponent>,
    private teamService: TeamsService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.idTeam = data.id;
    this.projectNameFC = new FormControl('');
    this.accessCodeFC = new FormControl('');

    this.projectForm = new FormGroup({
      projectNameFC: this.projectNameFC,
      accessCodeFC: this.accessCodeFC
    })
  }

  save() {
    this.teamService.joinProject(this.idTeam, this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  getData() {
    return {
      name: this.projectNameFC.value,
      accessCode: this.accessCodeFC.value
    }
  }
}
