import { Injectable } from "@angular/core";
import { AuthConfig, OAuthService } from "angular-oauth2-oidc";



@Injectable({
    providedIn: 'root'
})
export class AuthService {

    authCodeFlowConfig: AuthConfig = {
        // URL of identity provider. https://<YOUR_DOMAIN>.auth0.com
        issuer: 'https://dev-3m7i45aa.us.auth0.com/',
        redirectUri: window.location.origin + "/landingpage",
        clientId: 'fs8JpEFi6QwL63DwzNVGG83mynlgVHBW',
        responseType: 'code',
        scope: 'openid profile admin',
        showDebugInformation: true,
        silentRefreshRedirectUri: window.location.origin + "/landingpage",
        useSilentRefresh: true,
        customQueryParams: {
            /**
             * replace with your API-Audience
             * This is very important to retrieve a valid access_token for our API
             * */
            audience: 'http://localhost:8080',
        },
    };

    constructor(private oauth: OAuthService) {
        this.oauth.configure(this.authCodeFlowConfig);
        this.oauth.loadDiscoveryDocumentAndTryLogin();
        this.oauth.setupAutomaticSilentRefresh();
    }

    login(): void {
        this.oauth.initLoginFlow();
    }
}