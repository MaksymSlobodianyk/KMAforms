import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LandingPageComponent} from './components/landing-page/landing-page.component';
import {MainPageComponent} from './components/main-page/main-page.component';
import {AuthGuard} from './guards/auth.guard';
import {LandingGuard} from './guards/landing.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'landing',
    component: LandingPageComponent,
    canActivate: [LandingGuard]
  },
  {
    path: 'home',
    component: MainPageComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
