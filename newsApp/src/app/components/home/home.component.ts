import { DOCUMENT } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MessageSpan } from '@angular/compiler/src/i18n/i18n_ast';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from '@auth0/auth0-angular';
import { lastValueFrom } from 'rxjs';
import { Article } from 'src/app/models';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  articles!: Article[];
  isAuthenticated: User | undefined | null = this.auth.user$.subscribe(user => this.isAuthenticated = user);


  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService, private http: HttpClient, private newsSvc: NewsService, private router: Router) {
  }
  async ngOnInit() {
    await this.auth.isAuthenticated$.subscribe(bool => {
      if (bool === true) {
        this.router.navigate(['/landingpage']);
      }
    })

  }

}
