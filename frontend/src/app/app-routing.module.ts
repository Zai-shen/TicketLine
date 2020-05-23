import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { RegisterComponent } from './components/register/register.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AdminGuard } from './guards/admin.guard';
import { EventsComponent } from './components/events/events.component';
import { LocationComponent } from './components/location/location.component';
import { CreateLocationComponent } from './components/create-location/create-location.component';
import { CreateEventComponent } from './components/create-event/create-event.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'events', component: EventsComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/changePassword', canActivate: [AdminGuard], component: ChangePasswordComponent },
  { path: 'location', component: LocationComponent},
  { path: 'location/addLocation', canActivate: [AdminGuard], component: CreateLocationComponent },
  { path: 'event/newEvent', canActivate: [AdminGuard], component: CreateEventComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
