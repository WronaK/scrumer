import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-join-team',
  templateUrl: './join-team.component.html',
  styleUrls: ['./join-team.component.scss']
})
export class JoinTeamComponent implements OnInit {
  projectNameFC: FormControl;
  passwordFC: FormControl;
  projectForm: FormGroup;
  joinTeamForm: FormGroup;
  teamNameFC: FormControl;
  accessCodeFC: FormControl;

  constructor() {
    this.passwordFC = new FormControl('', Validators.required);
    this.projectNameFC = new FormControl('', Validators.required);
    this.projectForm = new FormGroup({
      projectNameFC: this.projectNameFC,
      passwordFC: this.passwordFC,
    });

    this.teamNameFC = new FormControl('', Validators.required);
    this.accessCodeFC = new FormControl('', Validators.required);
    this.joinTeamForm = new FormGroup({
      teamNameFC: this.teamNameFC,
      accessCodeFC: this.accessCodeFC,
    });
  }

  ngOnInit(): void {
  }

}
