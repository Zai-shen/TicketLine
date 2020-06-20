import { Component, OnInit, ViewChild } from '@angular/core';
import { PerformanceDTO, SearchPerformanceDTO } from '../../../generated';
import { PerformanceService } from '../../services/performance.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'tl-performance-list',
  templateUrl: './performance-list.component.html',
  styleUrls: ['./performance-list.component.css']
})
export class PerformanceListComponent implements OnInit {

  readonly LIST_PAGE_SIZE = 25;

  performances: PerformanceDTO[];
  performanceSearch: FormGroup;
  private currentPage = 0;
  searched: boolean;
  totalAmountOfPerformances = 0;

  selectedTime = '';
  selectedDate: Date | undefined;

  @ViewChild(ErrorMessageComponent)
  private errorMessageComponent: ErrorMessageComponent;

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  constructor(private formBuilder: FormBuilder, private performanceService: PerformanceService,
    private readonly datePipe: DatePipe) { }

  ngOnInit(): void {
      this.getAllPerformances();
      this.formInit();
  }

  formInit(): void {
    this.performanceSearch = this.formBuilder.group({
      date: [''],
      time: [''],
      price: [''],
      event: [''],
      location: [''],
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

  clearSearch(): void {
    this.searched = false;
    this.performanceSearch.reset();
    this.formInit();
    this.selectedDate = undefined;
    this.paginator.firstPage();
    this.getAllPerformances();
  }


  newSearch(): void {
    this.paginator.firstPage();
    this.searchPerformances();
  }

  private getAllPerformances(): void {
    this.performanceService.getAllPerformances(this.currentPage).subscribe(performances => {
        if (performances.body !== null) {
          this.performances = performances.body;
          this.totalAmountOfPerformances = Number(performances.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  searchPerformances(): void {
    const searchPerformanceDTO: SearchPerformanceDTO = Object.assign({}, this.performanceSearch.value);
    searchPerformanceDTO.date = this.datePipe.transform(this.selectedDate, 'yyyy-MM-dd') || '';
    this.searched = true;

    this.performanceService.searchPerformances(searchPerformanceDTO, this.currentPage).subscribe(performances => {
        if (performances.body !== null) {
          this.performances = performances.body;
          this.totalAmountOfPerformances = Number(performances.headers.get('X-Total-Count')) || 0;
        }
      },
      error => this.errorMessageComponent.defaultServiceErrorHandling(error));
  }

  dateChange(event: MatDatepickerInputEvent<any>) {
    this.selectedDate = event.value ? event.value : undefined;
  }

  timeChange(time: string) {
    this.selectedTime = time;
  }

}
