import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PerformanceDTO } from '../../../../generated';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { CreatePerformanceModalComponent } from '../create-performance-modal/create-performance-modal.component';
import { cloneDeep } from 'lodash-es';

@Component({
  selector: 'tl-performance-table',
  templateUrl: './performance-table.component.html',
  styleUrls: ['./performance-table.component.scss']
})
export class PerformanceTableComponent implements OnInit {
  @Input()
  performances: PerformanceDTO[];

  @Output()
  performancesChanged: EventEmitter<PerformanceDTO[]> = new EventEmitter<PerformanceDTO[]>();

  performanceColumns: string[] = ['location', 'datetime', 'edit', 'delete'];

  constructor(private readonly dialog: MatDialog) { }

  ngOnInit(): void {
  }

  onEdit(performance: PerformanceDTO) {
    const editIndex = this.performances.findIndex(item => item === performance);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = false;
    dialogConfig.data = cloneDeep(performance);
    dialogConfig.disableClose = true;

    const dialogRef = this.dialog.open(CreatePerformanceModalComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const newPerformances = cloneDeep(this.performances);
        Object.assign(newPerformances[editIndex], result);
        this.performancesChanged.emit(newPerformances);
      }
    });
  }

  onDelete(performance: PerformanceDTO) {
    const deleteIndex = this.performances.findIndex(item => item === performance);
    if (deleteIndex !== -1) {
      const newPerformances = cloneDeep(this.performances);
      newPerformances.splice(deleteIndex, 1);
        this.performances.splice(this.performances.findIndex(item => item === performance), 1);
      this.performancesChanged.emit(newPerformances);
    }
  }

  addPerformance() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = false;
    dialogConfig.disableClose = true;
    const dialogRef = this.dialog.open(CreatePerformanceModalComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.performancesChanged.emit((this.performances || []).concat([result]));
      }
    });
  }
}
