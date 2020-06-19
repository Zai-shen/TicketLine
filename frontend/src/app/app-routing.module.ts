import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminGuard } from './guards/admin.guard';
import { EventsComponent } from './components/events/events.component';
import { ArtistsComponent } from './components/artists/artists.component';
import { EventDetailComponent } from './components/event-detail/event-detail.component';
import { LocationListComponent } from './components/location-list/location-list.component';
import { CreateLocationComponent } from './components/create-location/create-location.component';
import {NewsComponent} from './components/news/news.component';
import {CreateNewsComponent} from './components/create-news/create-news.component';
import { AuthGuard } from './guards/auth.guard';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { CreateEventComponent } from './components/create-event/create-event.component';
import { TicketListComponent } from './components/ticket-list/ticket-list.component';
import { PerformanceListComponent } from './components/performance-list/performance-list.component';
import { NewsDetailComponent } from './components/news-detail/news-detail.component';
import { TopTenComponent } from './components/top-ten/top-ten.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'events', component: EventsComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/users', canActivate: [AdminGuard], component: UserListComponent },
  { path: 'artists', component: ArtistsComponent },
  { path: 'events/top', component: TopTenComponent},
  { path: 'events/:id', component: EventDetailComponent},
  { path: 'location', component: LocationListComponent},
  { path: 'location/addLocation', canActivate: [AdminGuard], component: CreateLocationComponent },
  { path: 'news', canActivate: [AuthGuard], component: NewsComponent },
  { path: 'news/addNews', canActivate: [AdminGuard], component: CreateNewsComponent },
  { path: 'news/:id', component: NewsDetailComponent },
  { path: 'user/settings', canActivate: [AuthGuard], component: UserDetailComponent },
  { path: 'event/newEvent', canActivate: [AdminGuard], component: CreateEventComponent },
  { path: 'user/tickets', canActivate: [AuthGuard], component: TicketListComponent },
  { path: 'event/newEvent', canActivate: [AdminGuard], component: CreateEventComponent },
  { path: 'performances', component: PerformanceListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
