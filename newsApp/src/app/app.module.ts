import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SearchComponent } from './components/search/search.component';
import { ResultComponent } from './components/result/result.component';
import { FavoritesComponent } from './components/favorites/favorites.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Auth0ClientFactory, AuthGuard, AuthHttpInterceptor, AuthState } from '@auth0/auth0-angular';
import { AuthModule } from '@auth0/auth0-angular';



import { NewsService } from './new.service';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { UsecaseComponent } from './components/usecase/usecase.component';
import { RegisterComponent } from './components/register/register.component';
import { ArticlesComponent } from './components/articles/articles.component';
import { LandingpageComponent } from './components/landingpage/landingpage.component';
import { AuthGuardService } from './authguard.service';
import { AuthService } from './auth.service';
import { QueryComponent } from './components/query/query.component';

const appRoutes: Routes = [
  { path: "", component: HomeComponent, canActivate: [AuthGuardService] },
  { path: "search", component: SearchComponent, canActivate: [AuthGuard] },
  { path: "about", component: AboutComponent },
  { path: "usecases", component: UsecaseComponent },
  { path: "landingpage", component: LandingpageComponent, canActivate: [AuthGuard] },
  { path: "query", component: QueryComponent },
  { path: "landingpage/:id", component: ArticlesComponent, canActivate: [AuthGuard] },
  { path: "article/:query", component: ResultComponent, canActivate: [AuthGuard] },
  { path: 'favorites', component: FavoritesComponent, canActivate: [AuthGuard] }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SearchComponent,
    ResultComponent,
    FavoritesComponent,
    HomeComponent,
    AboutComponent,
    UsecaseComponent,
    RegisterComponent,
    ArticlesComponent,
    LandingpageComponent,
    QueryComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    AuthModule.forRoot({
      domain: 'https://dev-3m7i45aa.us.auth0.com',
      clientId: 'fs8JpEFi6QwL63DwzNVGG83mynlgVHBW',
      // Request this audience at user authentication time
      audience: 'http://locahost:8080',
      // Request this scope at user authentication time
      scope: 'read:current_user',
      // Specify configuration for the interceptor  
      httpInterceptor: {
        allowedList: [
          {
            // Match any request that starts 'https://YOUR_DOMAIN/api/v2/' (note the asterisk)
            uri: 'https://newsview-ai.herokuapp.com/*',
            tokenOptions: {
              // The attached token should target this audience
              audience: 'http://locahost:8080',

              // The attached token should have these scopes
              scope: 'read:current_user'
            }
          }
        ]
      }
    }),
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true },

    // { provide: APP_INITIALIZER, useFactory: (authState: AuthState) => authState.isAuthenticated$, deps: [AuthGuardService], multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
