import { Component, Input, OnInit } from '@angular/core';
import { format, formatDistanceToNow } from 'date-fns';
import { de } from 'date-fns/locale';

@Component({
  selector: 'tl-timestamp',
  templateUrl: './relative-timestamp.component.html'
})
export class RelativeTimestampComponent implements OnInit {

  private _timestamp: Date = new Date();

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  set timestamp(source: string) {
    this._timestamp = new Date(source);
  }

  get relativeDate(): string {
    return formatDistanceToNow(this._timestamp, {locale: de, addSuffix: true});
  }

  get absoluteDate(): string {
    return format(this._timestamp, 'd MMM y - HH:mm', {locale: de});
  }
}
