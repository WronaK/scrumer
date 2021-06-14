import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'removeUnderscores'
})
export class RemoveUnderscoresPipe implements PipeTransform {

  transform(value: string): string {
    value = value.replace(/\_/g, ' ');
    return value;
  }
}
