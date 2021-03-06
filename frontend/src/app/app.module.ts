import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { httpInterceptorProviders } from './interceptors';
import { EventsComponent } from './components/events/events.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { RegisterComponent } from './components/register/register.component';
import { ErrorMessageComponent } from './components/error-message/error-message.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { EventDetailComponent } from './components/event-detail/event-detail.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { ArtistsComponent } from './components/artists/artists.component';
import { CustomPaginatorComponent } from './components/custom-paginator/custom-paginator.component';
import { LocationListComponent } from './components/location-list/location-list.component';
import { CreateLocationComponent } from './components/create-location/create-location.component';
import { NewsComponent } from './components/news/news.component';
import { CreateNewsComponent } from './components/create-news/create-news.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { CreatePerformanceModalComponent } from './components/create-event/create-performance-modal/create-performance-modal.component';
import { MatTableModule } from '@angular/material/table';
import { PerformanceTableComponent } from './components/create-event/performance-table/performance-table.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { UserListComponent } from './components/user-list/user-list.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { DatePipe } from '@angular/common';
import { TicketListComponent } from './components/ticket-list/ticket-list.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { PerformanceListComponent } from './components/performance-list/performance-list.component';
import { CreateArtistModalComponent } from './components/create-event/create-artist-modal/create-artist-modal.component';
import { LocationPerformancesSheetComponent } from './components/location-list/location-performances-sheet/location-performances-sheet.component';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { SeatplannerComponent } from './components/seatplanner/seatplanner.component';
import { SeatgroupPropertiesComponent } from './components/seatplanner/seatgroup-properties/seatgroup-properties.component';
import { MatTabsModule } from '@angular/material/tabs';
import { NewsDetailComponent } from './components/news-detail/news-detail.component';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RelativeTimestampComponent } from './components/relative-timestamp/relative-timestamp.component';
import { SeatplanComponent } from './components/seatplan/seatplan.component';
import { SelectStandingareaDialogComponent } from './components/seatplan/select-standingarea-dialog/select-standingarea-dialog.component';
import { TopTenComponent } from './components/top-ten/top-ten.component';
import { ConfirmUserDeletionModalComponent } from './components/user-detail/confirm-user-deletion-modal/confirm-user-deletion-modal.component';
import { SelectRoleDialogComponent } from './components/user-list/select-role-dialog/select-role-dialog.component';
import { MatRadioModule, MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { ArtistDropdownComponent } from './components/artist-dropdown/artist-dropdown.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    EventsComponent,
    RegisterComponent,
    ErrorMessageComponent,
    ChangePasswordComponent,
    EventDetailComponent,
    LocationListComponent,
    CreateLocationComponent,
    NewsComponent,
    NewsDetailComponent,
    CreateNewsComponent,
    UserDetailComponent,
    ArtistsComponent,
    UserListComponent,
    CreateEventComponent,
    CreatePerformanceModalComponent,
    CreateArtistModalComponent,
    PerformanceTableComponent,
    TicketListComponent,
    PerformanceListComponent,
    LocationPerformancesSheetComponent,
    SeatplannerComponent,
    SeatgroupPropertiesComponent,
    RelativeTimestampComponent,
    TopTenComponent,
    SeatplanComponent,
    SelectStandingareaDialogComponent,
    ConfirmUserDeletionModalComponent,
    SelectRoleDialogComponent,
    ArtistDropdownComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    MatDividerModule,
    MatGridListModule,
    MatRippleModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatToolbarModule,
    MatMenuModule,
    MatSnackBarModule,
    MatButtonToggleModule,
    MatDialogModule,
    MatTableModule,
    MatDialogModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatNativeDateModule,
    NgxMaterialTimepickerModule,
    MatExpansionModule,
    MatBottomSheetModule,
    MatListModule,
    MatTooltipModule,
    MatTabsModule,
    MatRadioModule,
    MatSlideToggleModule,
  ],
  providers: [
    {
      provide: MatPaginatorIntl,
      useClass: CustomPaginatorComponent
    },
    {
      provide: MAT_RADIO_DEFAULT_OPTIONS,
      useValue: { color: 'primary' },
    },
    httpInterceptorProviders,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
