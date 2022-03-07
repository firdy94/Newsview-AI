import { DOCUMENT } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from '@auth0/auth0-angular';
import { Article } from 'src/app/models';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  favArticles!: Article[];
  isAuthenticated: User | undefined | null = this.auth.user$.subscribe(user => this.isAuthenticated = user);

  constructor(@Inject(DOCUMENT) public document: Document, public auth: AuthService, private http: HttpClient, private newsSvc: NewsService, private router: Router) { }

  async ngOnInit() {
    await this.auth.user$.subscribe(user => this.isAuthenticated = user);
    if (this.isAuthenticated?.email != undefined) {
      this.newsSvc.getFavArticles().then(articles => {
        this.favArticles = articles;
      })
    }
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

  onDeleteArticle(id: string, i: number) {
    this.newsSvc.deleteFavArticle(id);
    this.favArticles.splice(i, 1);
  }

}
