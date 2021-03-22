import { Component, OnInit } from '@angular/core';
import {PRODUCT_BACKLOG} from "../mock/mock-product-backlog";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  tasks = PRODUCT_BACKLOG;

  constructor() { }

  ngOnInit(): void {
  }

}
