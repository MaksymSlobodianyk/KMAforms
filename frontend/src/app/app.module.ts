import { BrowserModule } from '@angular/platform-browser';
import {NgModule, Provider} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import uk from '@angular/common/locales/uk';
import {MsalModule} from '@azure/msal-angular';
import {environment} from '../environments/environment';
import {ToastNotificationsModule} from 'ngx-toast-notifications';
import {SharedModule} from './shared/shared.module';
import {TokenAppenderInterceptor} from './auth/token-appender.interceptor';
import { CreateFormComponent } from './components/create-form/create-form.component';
import { CreateChapterComponent } from './components/create-form/create-chapter/create-chapter.component';
import { CreateQuestionComponent } from './components/create-form/create-chapter/create-question/create-question.component';
import { HeaderComponent } from './components/header/header.component';
import { FormsListComponent } from './components/forms-list/forms-list.component';
import { RolesManagementComponent } from './components/roles-management/roles-management.component';
import { PassedFormsListComponent } from './components/passed-forms-list/passed-forms-list.component';
import { CreatedFormsListComponent } from './components/created-forms-list/created-forms-list.component';
import { SuggestedFormsListComponent } from './components/suggested-forms-list/suggested-forms-list.component';
import { FillFormComponent } from './components/fill-form/fill-form.component';

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
    MainPageComponent,
    CreateFormComponent,
    CreateChapterComponent,
    CreateQuestionComponent,
    MainPageComponent,
    HeaderComponent,
    FormsListComponent,
    RolesManagementComponent,
    PassedFormsListComponent,
    CreatedFormsListComponent,
    SuggestedFormsListComponent,
    FillFormComponent,
  ],
  imports: [
    BrowserModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
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
