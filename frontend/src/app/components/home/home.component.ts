import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import { PlaceholderpdfService } from '../../services/placeholderpdf.service';

@Component({
  selector: 'tl-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public authService: AuthService, private readonly placeholderpdfService: PlaceholderpdfService) { }

  ngOnInit() {
  }

  downloadTestPdf() {
    this.placeholderpdfService.downloadPlaceholderPdf();
  }
}
