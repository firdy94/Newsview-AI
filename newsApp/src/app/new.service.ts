import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Article, ArticleAnalysis, message } from "./models";


@Injectable({
    providedIn: 'root'
})
export class NewsService {

    constructor(private http: HttpClient) { }
    //TODO add /secure later if can
    getNews(): Promise<Article[]> {
        let observ = lastValueFrom(this.http.get<Article[]>(`https://newsview-ai-app.herokuapp.com/api/login`));
        // let observ = lastValueFrom(this.http.get<Article[]>(`http://localhost:8080/api/login`));
        console.log(observ);
        return observ;
    }
    getArticleAnalysis(id: string): Promise<ArticleAnalysis> {
        let observ = lastValueFrom(this.http.get<ArticleAnalysis>(`https://newsview-ai-app.herokuapp.com/api/article/${id}`));
        // let observ = lastValueFrom(this.http.get<ArticleAnalysis>(`http://localhost:8080/api/article/${id}`));

        console.log(observ);
        return observ;
    }

    sendEmail(msg: message) {
        let httpP = new HttpParams()
            .append("name", msg.name)
            .append("email", msg.email)
            .append("message", msg.message)

        const httpOptions = {
            headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded ' })
        }
        let observ = lastValueFrom(this.http.post<any>('https://newsview-ai-app.herokuapp.com/api/query', JSON.stringify(msg)));
        // let observ = lastValueFrom(this.http.post<any>('http://localhost:8080/api/query', httpP, httpOptions));

        console.log(observ);
        return observ;
    }

    getSearchAnalysis(query: string): Promise<[ArticleAnalysis[], ArticleAnalysis[]]> {
        let httpP = new HttpParams()
            .append("query", query)
        let observ = lastValueFrom(this.http.get<[ArticleAnalysis[], ArticleAnalysis[]]>(`https://newsview-ai-app.herokuapp.com/api/search`, { params: httpP }));
        // let observ = lastValueFrom(this.http.get<[ArticleAnalysis[], ArticleAnalysis[]]>(`http://localhost:8080/api/search/`, { params: httpP }));

        console.log(observ);
        return observ;
    }

    getFavArticles(): Promise<Article[]> {
        let observ = lastValueFrom(this.http.get<any>(`https://newsview-ai-app.herokuapp.com/api/favourites/get`));
        // let observ = lastValueFrom(this.http.get<any>(`http://localhost:8080/api/favourites/get`));

        console.log(observ);
        return observ;
    }

    deleteFavArticle(id: string) {
        let httpP = new HttpParams()
            .append("id", id)
        let observ = lastValueFrom(this.http.get<any>(`https://newsview-ai-app.herokuapp.com/api/favourites/delete`, { params: httpP }));
        // let observ = lastValueFrom(this.http.get<any>(`http://localhost:8080/api/favourites/delete`, { params: httpP }));

        console.log(observ);
        return observ;
    }
    addFavArticles(id: string): Promise<Article[]> {
        let observ = lastValueFrom(this.http.get<any>(`https://newsview-ai-app.herokuapp.com/api/favourites/add/${id}`));
        // let observ = lastValueFrom(this.http.get<any>(`http:localhost:8080/api/favourites/add/${id}`));

        console.log(observ);
        return observ;
    }
}

