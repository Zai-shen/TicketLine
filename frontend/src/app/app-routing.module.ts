import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AdminGuard } from './guards/admin.guard';
import { EventsComponent } from './components/events/events.component';
import { EventDetailComponent } from './components/event-detail/event-detail.component';
import { LocationComponent } from './components/location/location.component';
import { CreateLocationComponent } from './components/create-location/create-location.component';
import { UserListComponent } from './components/user-list/user-list.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'events', component: EventsComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/users', canActivate: [AdminGuard], component: UserListComponent },
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
