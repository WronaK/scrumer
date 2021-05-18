import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {CreateTeam} from "../../model/create.team";
import {TeamsService} from "../teams.service";
import {Member} from "../../model/member";

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.scss']
})
export class AddTeamComponent implements OnInit {
  teamForm: FormGroup;
  teamNameFC: FormControl;
  passwordFC: FormControl;

  membersForm: FormGroup;
  emailMembersFC: FormControl;

  members: Member[] = [];

  constructor(private dialogRef: MatDialogRef<AddTeamComponent>,
              private teamService: TeamsService) {
    this.passwordFC = new FormControl('', Validators.required);
    this.teamNameFC = new FormControl('', Validators.required);
    this.teamForm = new FormGroup({
      projectNameFC: this.teamNameFC,
      passwordFC: this.passwordFC
    });
    this.emailMembersFC = new FormControl('', Validators.email);
    this.membersForm = new FormGroup({
      emailMembersFC: this.emailMembersFC
    })
  }

  ngOnInit(): void {
  }

  save() {
    this.teamService.createTeam(this.getData())
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
}
