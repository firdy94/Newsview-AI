import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  constructor(private activatedRoute: ActivatedRoute, private newsSvc: NewsService, private router: Router) { }
  async ngOnInit() {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.article = await this.newsSvc.getArticleAnalysis(this.id);
  }
}
