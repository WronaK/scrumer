import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RemoveUnderscoresPipe } from './remove-underscores.pipe';

@NgModule({
  declarations: [RemoveUnderscoresPipe],
  imports: [
    CommonModule
  ],
  exports: [RemoveUnderscoresPipe]
})
export class PipesModule { }
