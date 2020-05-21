import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import { EventApiService, EventDTO } from '../../../generated';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'tl-home',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit {

  public event: EventDTO;

  public errorMsg?: string;

  constructor(private eventService: EventApiService, private route: ActivatedRoute) { }

  public seats = Array.from(Array(32).keys());

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.eventService.getEvent(+params['id']).subscribe(
        (event: EventDTO) => {
          this.event = event;
        },
        error => {
          if(error.status === 404) {
            this.errorMsg = 'Das angeforderte Event konnte nicht gefunden werden';
          }
        }
      );
    });
  }

}
