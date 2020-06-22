import { Component, OnInit } from '@angular/core';
import { BookingDTO, FreeSeatgroupBookingDTO, TicketApiService } from '../../../generated';
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



  ngOnInit() {
    this.ticketService.getTicketsOfUser().subscribe(value => {
      this.bookings = value;
    });
  }

  downloadInvoice(ticketId: Number): void {
    this.bookingService.renderInvoice(ticketId);
  }

  sumStanding(free: FreeSeatgroupBookingDTO[]): number {
    return free.reduce((a, b) => a + b.amount, 0);
  }

  downloadTicket(booking: BookingDTO) {
    if (booking.id) {
      this.bookingService.renderTicket(booking.id);
    }
  }
}
