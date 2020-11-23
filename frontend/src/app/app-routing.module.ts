import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LandingPageComponent} from './components/landing-page/landing-page.component';
import {MainPageComponent} from './components/main-page/main-page.component';
import {AuthGuard} from './guards/auth.guard';
import {LandingGuard} from './guards/landing.guard';
import {CreateFormComponent} from './components/create-form/create-form.component';
import {FormsListComponent} from './components/forms-list/forms-list.component';
import {RolesManagementComponent} from './components/roles-management/roles-management.component';
import {PassedFormsListComponent} from './components/passed-forms-list/passed-forms-list.component';
import {SuggestedFormsListComponent} from './components/suggested-forms-list/suggested-forms-list.component';
import {FillFormComponent} from './components/fill-form/fill-form.component';

const mainPageChildRoutes = [
  {
    path: '',
    component: FormsListComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: 'create',
    component: CreateFormComponent,
    canActivate: [AuthGuard]
  }
  ,
  {
    path: 'roles-management',
    component: RolesManagementComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'temp',
    component: SuggestedFormsListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'passed',
    component: PassedFormsListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'fill/:id',
    component: FillFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'review/:id',
    component: FillFormComponent,
    canActivate: [AuthGuard]
  }
];

const routes: Routes = [
  {
    path: '',
    redirectTo: '/me',
    pathMatch: 'full'
  },
  {
    path: 'landing',
    component: LandingPageComponent,
    canActivate: [LandingGuard]
  },
  {
    path: 'me',
    component: MainPageComponent,
    canActivate: [AuthGuard],
    children: mainPageChildRoutes
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
