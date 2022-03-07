import { DOCUMENT } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { Article } from 'src/app/models';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.css']
})
export class LandingpageComponent implements OnInit {

  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService, private http: HttpClient, private newsSvc: NewsService, private router: Router) { }

  articles!: Article[];
  isAuthenticated!: boolean;
  async ngOnInit() {
    await this.auth.isAuthenticated$.subscribe(user => this.isAuthenticated = user);
    this.newsSvc.getNews().then(articles => {
      this.articles = articles;
    })

  }



  updateUrl(event: any) {
    event.target.src = 'assets/images/notfound.png';
  }

  async onRequest() {

    // this.articles = await this.newsSvc.getNews()
  }

  onReadArticle(i: string) {
    this.router.navigate(['/article', i]);
  }


}
