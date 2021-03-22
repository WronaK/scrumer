import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {ProductBacklogComponent} from "./product-backlog/product-backlog.component";

const routes: Routes = [
  { path: '', component: MainLayoutComponent, children: [
      { path: '', component: ProductBacklogComponent }
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
