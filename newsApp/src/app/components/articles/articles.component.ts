import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
import { ArticleAnalysis } from 'src/app/models';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.css']
})
export class ArticlesComponent implements OnInit {

  id!: string;
  article!: ArticleAnalysis;
  isAuthenticated!: boolean;


  constructor(@Inject(DOCUMENT) public document: Document, private activatedRoute: ActivatedRoute, private newsSvc: NewsService, private router: Router, public auth: AuthService) { }

  async ngOnInit() {
    await this.auth.isAuthenticated$.subscribe(user => this.isAuthenticated = user);
    this.id = this.activatedRoute.snapshot.params['id'];
    this.article = await this.newsSvc.getArticleAnalysis(this.id);
  }

  updateUrl(event: any) {
    event.target.src = 'assets/images/notfound.png';
  }

}
