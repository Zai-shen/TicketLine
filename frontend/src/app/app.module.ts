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
import { LocationComponent } from './components/location/location.component';
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
import { MatNativeDateModule } from '@angular/material/core';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatRippleModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserListComponent } from './components/user-list/user-list.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { DatePipe } from '@angular/common';
import { TicketListComponent } from './components/ticket-list/ticket-list.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { NewsDetailComponent } from './components/news-detail/news-detail.component';

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
    LocationComponent,
    CreateLocationComponent,
    NewsComponent,
    CreateNewsComponent,
    UserDetailComponent,
    ArtistsComponent,
    UserListComponent,
    CreateEventComponent,
    CreatePerformanceModalComponent,
    PerformanceTableComponent,
    TicketListComponent,
    NewsDetailComponent
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
        MatExpansionModule
    ],
  providers: [
    {
      provide: MatPaginatorIntl,
      useClass: CustomPaginatorComponent
    },
    httpInterceptorProviders,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
