import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TeamsService} from "../teams.service";
import {Member} from "../../model/member";

@Component({
  selector: 'app-add-members',
  templateUrl: './add-members.component.html',
  styleUrls: ['./add-members.component.scss']
})
export class AddMembersComponent {
  membersForm: FormGroup;
  emailMembersFC: FormControl;
  members: Member[] = [];
  idTeam!: number;

  constructor(
    private dialogRef: MatDialogRef<AddMembersComponent>,
    private teamService: TeamsService,
    @Inject(MAT_DIALOG_DATA) data: any
  ) {
    this.idTeam = data.id;
    this.emailMembersFC = new FormControl('', Validators.email);
    this.membersForm = new FormGroup({
      emailMembersFC: this.emailMembersFC
    })
  }

  addMember() {
    this.members.push({email: this.emailMembersFC.value});
    this.emailMembersFC.reset();
  }

  save() {
    this.teamService.addMembers(this.idTeam, {members: this.members})
      .subscribe(() => this.dialogRef.close());
  }
}
