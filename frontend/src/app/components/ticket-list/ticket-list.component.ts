import { Component, OnInit } from '@angular/core';
import { TicketApiService, BookingDTO } from '../../../generated';
import { format, formatDistanceToNow } from 'date-fns';
import { de } from 'date-fns/locale';
import { BookingService } from '../../services/booking.service';

@Component({
  selector: 'tl-bookings',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.scss']
})
export class TicketListComponent implements OnInit {

  constructor(private readonly ticketService: TicketApiService, private readonly bookingService: BookingService) {

  }

  public bookings: BookingDTO[] = [];
  public reserved: boolean = false;

  get filteredBookings(): BookingDTO[] {
    if (!this.bookings) {
      return [];
    }
    return this.bookings.filter(b => b.reservation === this.reserved);
  }

  public formatTimestamp(timestamp: string): string {
    return formatDistanceToNow(new Date(timestamp), {locale: de, addSuffix: true});
  }

  public formatAbsolute(timestamp: string): string {
    return format(new Date(timestamp), 'd MMM y - HH:mm', {locale: de});
  }

  ngOnInit() {
    this.ticketService.getTicketsOfUser().subscribe(value => {
      this.bookings = value;
    });
  }

  downloadInvoice(ticketId: Number): void {
    this.bookingService.renderInvoice(ticketId);
  }

  downloadTicket(booking: BookingDTO) {
    this.bookingService.renderTicket(booking.id!!);
  }
}
