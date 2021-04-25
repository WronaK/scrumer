import { Component, OnInit } from '@angular/core';
import {PRODUCT_BACKLOG} from "../mock/mock-product-backlog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  tasks = PRODUCT_BACKLOG;
  projects = [{id: 1, name: "Project 1"}, {id: 2, name: "Project 2"}, {id: 3, name: "Project 3"}];
  teams = [{id: 1, name: "Team 1"}, {id: 2, name: "Team 2"}, {id: 3, name: "Team 3"}];

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  goToProject(id: number): void {
    this.router.navigate(['product-backlog/' + id]);
  }

  goToTeam(id: number): void {
    this.router.navigate(['sprint-backlog/' + id]);
  }
}
