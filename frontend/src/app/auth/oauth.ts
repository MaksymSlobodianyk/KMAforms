import {environment} from '../../environments/environment';

export const OAuthSettings = {
  appId: environment.clientId,
  redirectUri: environment.redirectUri,
  scopes: [
    'user.read'
  ]
};
