import { BrowserModule } from '@angular/platform-browser';
import {NgModule, Provider} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { FormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import uk from '@angular/common/locales/uk';
import {MsalModule} from '@azure/msal-angular';
import {environment} from '../environments/environment';
import {ToastNotificationsModule} from 'ngx-toast-notifications';
import {SharedModule} from './shared/shared.module';
import {TokenAppenderInterceptor} from './auth/token-appender.interceptor';

registerLocaleData(uk);

const INTERCEPTOR_PROVIDER: Provider = {
  provide: HTTP_INTERCEPTORS,
  multi: true,
  useClass: TokenAppenderInterceptor
};

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    MainPageComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
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
    }),
    AppRoutingModule
  ],
  providers: [INTERCEPTOR_PROVIDER],
  bootstrap: [AppComponent]
})
export class AppModule { }
