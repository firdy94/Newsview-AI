import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article, ArticleAnalysis } from 'src/app/models';
import { NewsService } from 'src/app/new.service';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  negArticles!: ArticleAnalysis[];
  posArticles!: ArticleAnalysis[];
  query!: string;
  allArticles!: [ArticleAnalysis[], ArticleAnalysis[]];

  constructor(private activatedRoute: ActivatedRoute, private newsSvc: NewsService, private router: Router) { }


  async ngOnInit() {
    this.query = this.activatedRoute.snapshot.params['query'];
    this.allArticles = await this.newsSvc.getSearchAnalysis(this.query);
    this.negArticles = this.allArticles[0];
    this.posArticles = this.allArticles[1];

  }

  updateUrl(event: any) {
    event.target.src = 'assets/images/notfound.png';
  }

  onReadArticle(i: string) {
    this.router.navigate(['/article', i]);
  }

}
