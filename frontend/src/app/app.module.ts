import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import uk from '@angular/common/locales/uk';
import {MsalModule} from '@azure/msal-angular';
import { ToastService, AngularToastifyModule } from 'angular-toastify';
import {environment} from '../environments/environment';
import {ToastNotificationsModule} from 'ngx-toast-notifications';

registerLocaleData(uk);

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    MainPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastNotificationsModule,
    MsalModule.forRoot({
      auth: {
        clientId: environment.clientId,
        authority: environment.authority,
        redirectUri: environment.redirectUri
      }
    })
  ],
  providers: [ToastService],
  bootstrap: [AppComponent]
})
export class AppModule { }
