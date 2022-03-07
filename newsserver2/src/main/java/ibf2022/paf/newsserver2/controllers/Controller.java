package ibf2022.paf.newsserver2.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.paf.newsserver2.models.Article;
import ibf2022.paf.newsserver2.repositories.NewsviewMongoRepository;
import ibf2022.paf.newsserver2.repositories.NewsviewRepository;
import ibf2022.paf.newsserver2.service.ArticleService;
import ibf2022.paf.newsserver2.service.EmailService;
import ibf2022.paf.newsserver2.service.NewsApiService;
import ibf2022.paf.newsserver2.service.NewsviewService;
import ibf2022.paf.newsserver2.service.SentimentService;
import ibf2022.paf.newsserver2.service.ToneAnalyzeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

	@Autowired
	NewsApiService newsApiSvc;

	@Autowired
	SentimentService sentimentSvc;

	@Autowired
	ToneAnalyzeService toneAnalyzeSvc;

	@Autowired
	ArticleService articleSvc;

	@Autowired
	NewsviewRepository newsviewRepo;

	@Autowired
	NewsviewMongoRepository newsviewMongoRepo;

	@Autowired
	NewsviewService newsviewSvc;

	@Autowired
	EmailService emailSvc;

	@GetMapping("/article/{id}")
	public ResponseEntity<String> getSentimentArticle(@PathVariable String id) {

		if (newsviewMongoRepo.findItemByUuid(id).isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		Article article = newsviewSvc.articleSetSentimentAndTone(id);
		if (newsviewSvc.articleResp(article).isEmpty() || newsviewSvc.articleResp(article).isBlank()) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newsviewSvc.articleResp(article));
	}

	@GetMapping("/search")
	public ResponseEntity<String> getSearchSentiments(@RequestParam String query) {
		System.out.println(query);
		List<Article> articlesArray = new ArrayList<>();
		String jsonResp = "";
		// try {
		// if (newsApiSvc.getNewsSearch(query).isEmpty() ||
		// newsApiSvc.getNewsSearch(query).isBlank()) {
		// return ResponseEntity.noContent().build();
		// }

		try {
			articlesArray = newsApiSvc.stringToArticles(
					newsApiSvc.getNewsSearch(query)).get()
					.stream().map(article -> article = newsviewSvc.articleSetSentimentAndTone(article.getId()))
					.collect(Collectors.toList());
			newsviewMongoRepo.saveAll(articlesArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		articlesArray.sort(
				(Article article1, Article article2) -> article1.getSentimentAnalysis().getSentiment().getScore()
						.compareTo(article2.getSentimentAnalysis().getSentiment().getScore()));
		Article[] negArticlesArray = articlesArray.stream().limit(3).collect(Collectors.toList())
				.toArray(new Article[0]);
		Collections.reverse(articlesArray);
		Article[] posArticlesArray = articlesArray.stream().limit(3).collect(Collectors.toList())
				.toArray(new Article[0]);

		List<Article[]> allArticlesArray = new ArrayList<>();
		allArticlesArray.add(negArticlesArray);
		allArticlesArray.add(posArticlesArray);

		jsonResp = newsviewSvc.articleArrayResp(allArticlesArray);
		return ResponseEntity.ok(jsonResp);
		// }catch(

		// Exception e)
		// {
		// e.printStackTrace();
		// return ResponseEntity.noContent().build();
		// }
	}

	@GetMapping("/login")
	public ResponseEntity<String> getArticles(@RequestHeader("authorization") String header) {
		try {
			newsviewSvc.saveUser(header);
		} catch (IOException e1) {
			e1.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

		List<Article> articlesArray = new ArrayList<>();
		String jsonResp = "";
		try {
			if (newsApiSvc.getBreakingNews().isEmpty() || newsApiSvc.getBreakingNews().isBlank()) {
				return ResponseEntity.noContent().build();
			}
			articlesArray = newsApiSvc.stringToArticles(newsApiSvc.getBreakingNews()).get();
			jsonResp = Article.createJsonObject(articlesArray).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(jsonResp);
	}

	@PostMapping("/query")
	public ResponseEntity<String> sendEmail(@RequestBody MultiValueMap<String, String> data) {

		String name = data.getFirst("name");
		String email = data.getFirst("email");
		String query = data.getFirst("message");
		String subject = "Hi %s! We have received your query!".formatted(name);
		String reply = "Hi %s!\nWe're glad to hear from you here at Newsview AI and look forward to answering any questins and queries that you may have.\nHere's a copy of the query you had sent to us for your reference.\n\n'%s'\nWe will get back to you on this within 3-5 business days.\nThank you.\nRegards,\nNewsview AI team."
				.formatted(name, query);
		emailSvc.sendEmail(email, reply, subject);
		return ResponseEntity.ok("query received");
	}

	@GetMapping("/favourites/get")
	public ResponseEntity<String> getFavourites(@RequestHeader String header) {

		String email;
		try {
			email = newsviewSvc.getUserEmail(header);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		if (newsviewRepo.getFavArticleIds(email).isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		List<Article> articles = new ArrayList<>();

		for (String id : newsviewRepo.getFavArticleIds(email).get()) {
			if (newsviewRepo.getArticleById(id).isEmpty()) {
				if (newsviewMongoRepo.findById(id).isPresent()) {
					articles.add(newsviewMongoRepo.findById(id).get());
				}
			}
			articles.add(newsviewRepo.getArticleById(id).get());
		}
		String jsonResp = Article.createJsonObject(articles).toString();
		return ResponseEntity.ok(jsonResp);
	}

	@GetMapping("/favourites/delete")
	public ResponseEntity<String> deleteFavourite(@RequestHeader String header, @RequestParam String id) {

		String email = "";
		try {
			email = newsviewSvc.getUserEmail(header);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		if (newsviewRepo.getFavArticleIds(email).isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		newsviewRepo.deleteFavArticle(id, email);
		return ResponseEntity.ok("deleted");
	}

	@GetMapping("/favourites/add/{id}")
	public ResponseEntity<String> addFavourite(@RequestHeader String header, @PathVariable String id) {
		String email = "";
		try {
			email = newsviewSvc.getUserEmail(header);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		newsviewRepo.addFavArticle(id, email);
		return ResponseEntity.ok("Favourite added");
	}

}
