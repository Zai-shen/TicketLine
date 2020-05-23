import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AdminGuard } from './guards/admin.guard';
import { EventsComponent } from './components/events/events.component';
import { ArtistsComponent } from './components/artists/artists.component';
import { EventDetailComponent } from './components/event-detail/event-detail.component';
import { LocationComponent } from './components/location/location.component';
import { CreateLocationComponent } from './components/create-location/create-location.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'events', component: EventsComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/changePassword', canActivate: [AdminGuard], component: ChangePasswordComponent },
  { path: 'artists', component: ArtistsComponent },
  { path: 'events/:id', component: EventDetailComponent},
  { path: 'location', component: LocationComponent},
  { path: 'location/addLocation', canActivate: [AdminGuard], component: CreateLocationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
