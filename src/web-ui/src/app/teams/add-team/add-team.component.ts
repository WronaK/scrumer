import {Component, Inject } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CreateTeam} from "../../model/create.team";
import {TeamsService} from "../teams.service";
import {Member} from "../../model/member";
import {tap} from "rxjs/operators";
import {Team} from "../model/team";

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.scss']
})
export class AddTeamComponent {
  teamForm: FormGroup;
  teamNameFC: FormControl;
  passwordFC: FormControl;

  membersForm!: FormGroup;
  emailMembersFC!: FormControl;
  request: string;
  idTeam!: number;
  team!: Team;

  members: Member[] = [];

  constructor(
    private dialogRef: MatDialogRef<AddTeamComponent>,
    private teamService: TeamsService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.request = data.request;
    this.passwordFC = new FormControl('', Validators.required);
    this.teamNameFC = new FormControl('', Validators.required);
    this.teamForm = new FormGroup({
      projectNameFC: this.teamNameFC,
      passwordFC: this.passwordFC
    });

    if(this.request == "ADD") {
      this.emailMembersFC = new FormControl('', Validators.email);
      this.membersForm = new FormGroup({
        emailMembersFC: this.emailMembersFC
      })
    } else if(this.request == "UPDATE") {
      this.idTeam = data.id;
      this.getTeam();
    }
  }

  save() {
    if(this.request == "UPDATE") {
      this.update();
    } else if(this.request == "ADD") {
      this.create();
    }
  }

  create() {
    this.teamService.createTeam(this.getData())
      .subscribe(() => this.dialogRef.close());
  }

  update() {
    this.teamService.updateTeam(this.getTeamToUpdate())
      .subscribe(() => this.dialogRef.close());
  }

  getData(): CreateTeam {
    return {
      name: this.teamNameFC.value,
      accessCode: this.passwordFC.value,
      members: this.members
    }
  }

  addMember() {
    this.members.push({email: this.emailMembersFC.value});
    this.emailMembersFC.reset();
  }

  setData() {
    this.teamNameFC.setValue(this.team.name);
    this.passwordFC.setValue(this.team.accessCode);
  }

  getTeamToUpdate() {
    return {
      id: this.idTeam,
      name: this.teamNameFC.value,
      accessCode: this.passwordFC.value
    }
  }

  getTeam() {
    this.teamService.getTeamById(this.idTeam)
      .pipe(tap(team => this.team = team))
      .subscribe(() => this.setData())
  }
}
