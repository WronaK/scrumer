import { Component, OnInit } from '@angular/core';
import {FormControl} from "@angular/forms";
import {ProjectDetailsService} from "../project-details.service";
import {ProjectDetails} from "../../model/project/project.details";

@Component({
  selector: 'app-information-project',
  templateUrl: './information-project.component.html',
  styleUrls: ['./information-project.component.scss']
})
export class InformationProjectComponent implements OnInit {
  projectNameFC: FormControl;
  descriptionFC: FormControl;
  passwordFC: FormControl;
  creatorFC: FormControl;
  productOwnerFC: FormControl;
  scrumMasterFC: FormControl;
  disabled = true;
  projectId!: number;

  constructor(private projectDetailsService: ProjectDetailsService) {
    this.projectNameFC = new FormControl({ value: '', disabled: this.disabled });
    this.descriptionFC = new FormControl({ value: '', disabled: this.disabled });
    this.passwordFC = new FormControl({ value: '', disabled: this.disabled });
    this.creatorFC = new FormControl({ value: '', disabled: this.disabled });
    this.productOwnerFC = new FormControl({ value: '', disabled: this.disabled });
    this.scrumMasterFC = new FormControl({ value: '', disabled: this.disabled });
  }

  ngOnInit(): void {
    this.projectDetailsService.uploadProject();
    this.projectDetailsService.getProject().subscribe(project => {if(project != null) this.setData(project)});
  }

  setData(project: ProjectDetails): void {
    this.projectId = project.id;
    this.projectNameFC.setValue(project.name);
    this.descriptionFC.setValue(project.description);
    this.passwordFC.setValue(project.accessCode);
    this.creatorFC.setValue(project.creatorName);
    this.productOwnerFC.setValue(project.productOwnerName);
    this.scrumMasterFC.setValue(project.scrumMasterName);
  }

}
