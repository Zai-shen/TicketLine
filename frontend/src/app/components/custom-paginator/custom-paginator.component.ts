import {MatPaginatorIntl} from '@angular/material/paginator';
import {Injectable} from '@angular/core';

@Injectable()
export class CustomPaginatorComponent extends MatPaginatorIntl {
  constructor() {
    super();

    this.getAndInitTranslations();
  }

  getAndInitTranslations() {
    this.itemsPerPageLabel = "Einträge pro Seite:";
    this.nextPageLabel = "nächste Seite";
    this.previousPageLabel = "vorherige Seite";
    this.changes.next();
  }

}
