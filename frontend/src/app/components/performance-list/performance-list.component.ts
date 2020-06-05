import { Component, OnInit, ViewChild } from '@angular/core';
import { PerformanceDTO, SearchPerformanceDTO } from '../../../generated';
import { PerformanceService } from '../../services/performance.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';

@Component({
  selector: 'tl-performance-list',
  templateUrl: './performance-list.component.html',
  styleUrls: ['./performance-list.component.css']
})
export class PerformanceListComponent implements OnInit {

  readonly LIST_PAGE_SIZE = 25;

  performances: PerformanceDTO[];
  performanceSearch: FormGroup;
  amountOfPages = 1;
  private currentPage = 0;
  searched: boolean;
  performancesFound = 0;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  constructor(private formBuilder: FormBuilder, private performanceService: PerformanceService) { }

  ngOnInit(): void {
      this.getAllPerformances();
      this.performanceSearch = this.formBuilder.group({
        date: [''],
        time: [''],
        price: [''],
        event: [''],
      });
  }

  onPaginationChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    if (this.searched) {
      this.searchPerformances();
    } else {
      this.getAllPerformances();
    }
  }

  private getAllPerformances(): void {
    this.performanceService.getAllPerformances(this.currentPage).subscribe(performances => {
        if (performances.body !== null) {
          this.performances = performances.body;
          this.amountOfPages = Number(performances.headers.get('X-Total-Count')) || 1;
          this.performancesFound = Number(performances.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  searchPerformances(): void {
    const searchPerformanceDTO: SearchPerformanceDTO = Object.assign({}, this.performanceSearch.value);

  }

}
