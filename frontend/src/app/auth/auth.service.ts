import {Injectable} from '@angular/core';
import {MsalService} from '@azure/msal-angular';

import {OAuthSettings} from './oauth';
import {User} from '../models/auth/user';
import {Client} from '@microsoft/microsoft-graph-client';
import * as MicrosoftGraph from '@microsoft/microsoft-graph-types';
import {Toaster} from 'ngx-toast-notifications';
import {Router} from '@angular/router';
import {AuthApiService} from '../api-services/auth-api.service';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  public authenticated: boolean;
  public user: User;
  public idToken: string;

  constructor(
    private msalService: MsalService,
    private  toaster: Toaster,
    private router: Router,
    private authApiService: AuthApiService,
  ) {
    this.authenticated = this.msalService.getAccount() != null;
    this.getUser().then((user) => {
      this.user = user;
    });
  }

  async signIn(): Promise<void> {
    const result = await this.msalService.loginPopup(OAuthSettings)
      .catch((err) => {
        if (err.errorCode === 'user_cancelled') {
          this.toaster.open({
            text: 'Щоб продовжити вхід потрібно натиснути Дозволити',
            caption: '😢   Упс...',
            duration: 4000,
            type: 'warning'
          });
        } else {
          this.toaster.open({
            text: 'Щось пішло не так. Будь ласка,спробуйте ще раз',
            caption: '😢   Упс...',
            duration: 4000,
            type: 'warning'
          });
        }
      });

    if (result) {
      this.router.navigateByUrl('/me');
      this.authenticated = true;
      this.user = await this.getUser();
    }
  }

  signOut(): void {
    this.msalService.logout();
    this.user = null;
    this.authenticated = false;
  }

  async getAccessToken(): Promise<string> {
    const result = await this.msalService.acquireTokenSilent(OAuthSettings)
      .catch((err) => {
        this.toaster.open({
          text: 'Нам не вдалося увійти з вашими данними. Будь ласка,спробуйте ще раз',
          caption: '😢 Упс...',
          duration: 4000,
          type: 'warning'
        });
      });

    if (result) {
      return result.accessToken;
    }

    this.authenticated = false;
    return null;
  }

  async getIdToken(): Promise<string> {
    const result = await this.msalService.acquireTokenSilent(OAuthSettings)
      .catch((err) => {
        this.toaster.open({
          text: 'Нам не вдалося увійти з вашими данними. Будь ласка,спробуйте ще раз',
          caption: '😢 Упс...',
          duration: 4000,
          type: 'warning'
        });
      });
    if (result) {
      return result.idToken.rawIdToken;
    }

    this.authenticated = false;
    return null;
  }

  private async getUser(): Promise<User> {
    if (!this.authenticated) {
      return null;
    }

    const graphClient = Client.init({
      authProvider: async (done) => {
        this.idToken = await this.getIdToken();
        const token = await this.getAccessToken()
          .catch((reason) => {
            done(reason, null);
          });

        if (token) {
          done(null, token);
        } else {
          done('Could not get an access token', null);
        }
      }
    });

    const graphUser: MicrosoftGraph.User = await graphClient
      .api('/me')
      .select('displayName,mail,userPrincipalName')
      .get();
    const user = new User();
    user.displayName = graphUser.displayName;
    user.email = graphUser.mail || graphUser.userPrincipalName;
    user.avatar = '/assets/mock.png';
    const authenticatedUser = await this.authApiService.authenticate();
    user.role = authenticatedUser.role;
    return user;
  }
}
