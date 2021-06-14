import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MaterialModule} from "../material.module";
import {SprintBacklogComponent} from "./sprint-backlog/sprint-backlog.component";
import {TaskStatesComponent} from "./task-states/task-states.component";
import {ReactiveFormsModule} from "@angular/forms";
import {DragDropModule} from "@angular/cdk/drag-drop";
import { AddTeamComponent } from './add-team/add-team.component';
import {MatCardModule} from "@angular/material/card";
import { TeamComponent } from './team/team.component';
import { InformationTeamComponent } from './information-team/information-team.component';
import { MenuTeamComponent } from './menu-team/menu-team.component';
import { MembersComponent } from './members/members.component';
import { ProjectsComponent } from './projects/projects.component';
import {SharedModule} from "../shared/shared.module";
import { JoinProjectComponent } from './join-project/join-project.component';
import { AddMembersComponent } from './add-members/add-members.component';
import {ShowTaskFromSprintBacklogComponent} from "./show-task-from-sprint-backlog/show-task-from-sprint-backlog.component";
import { DividedIntoTasksComponent } from './divided-into-tasks/divided-into-tasks.component';
import {PipesModule} from "../pipes/pipes.module";
import { JoinTeamComponent } from './join-team/join-team.component';

@NgModule({
  declarations: [
    SprintBacklogComponent,
    TaskStatesComponent,
    AddTeamComponent,
    TeamComponent,
    InformationTeamComponent,
    MenuTeamComponent,
    MembersComponent,
    ProjectsComponent,
    JoinProjectComponent,
    AddMembersComponent,
    ShowTaskFromSprintBacklogComponent,
    DividedIntoTasksComponent,
    JoinTeamComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    DragDropModule,
    MatCardModule,
    SharedModule,
    PipesModule,
  ],
  exports: [
    TeamComponent
  ]
})
export class TeamsModule { }
