package ibf2022.paf.newsserver2.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.paf.newsserver2.models.Article;
import ibf2022.paf.newsserver2.repositories.NewsviewMongoRepository;
import ibf2022.paf.newsserver2.repositories.NewsviewRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class NewsApiService {
	@Autowired
	NewsviewMongoRepository newsviewMongoRepo;

	@Autowired
	NewsviewRepository newsviewRepo;

	static final Logger logger = Logger.getLogger(NewsApiService.class.getName());

	public String getNewsSearch(String query) {
		query = NewsApiService.normaliseString(query);
		String apiKey = System.getenv("newsApiKey");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String maxDateSearch = sdf.format(new Date(Instant.now().toEpochMilli()));
		String url = UriComponentsBuilder
				.fromUriString("https://newsapi.org/v2/everything")
				.queryParam("q", query)
				.queryParam("from", maxDateSearch)
				.queryParam("pageSize", 6)
				.queryParam("page", 1)
				.queryParam("language", "en")
				.queryParam("apiKey", apiKey)
				.toUriString();
		RequestEntity<Void> req = RequestEntity.get(url).build();
		RestTemplate template = new RestTemplate();
		String respBody = "No articles found for search";
		try {
			ResponseEntity<String> resp = template.exchange(req, String.class);
			respBody = resp.getBody();
		} catch (HttpClientErrorException e) {
			logger.info(e.getResponseBodyAsString());
		}
		return respBody;
	}

	public String getBreakingNews() {
		String apiKey = System.getenv("newsApiKey");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String maxDateSearch = sdf.format(new
		// Date(Instant.now().minusMillis(1209600000).toEpochMilli()));
		String url = UriComponentsBuilder
				.fromUriString("https://newsapi.org/v2/top-headlines")
				// .queryParam("from", maxDateSearch)
				.queryParam("language", "en")
				.queryParam("pageSize", "5") // TODO change to 20 once prod
				.queryParam("country", "sg")
				.queryParam("apiKey", apiKey)
				.toUriString();
		RequestEntity<Void> req = RequestEntity.get(url).build();
		RestTemplate template = new RestTemplate();
		String respBody = "No articles found for search";
		try {
			ResponseEntity<String> resp = template.exchange(req, String.class);
			respBody = resp.getBody();
		} catch (HttpClientErrorException e) {
			logger.info(e.getResponseBodyAsString());
		}
		System.out.println(respBody);
		return respBody;
	}

	public Optional<List<Article>> stringToArticles(String jsonString) throws Exception {
		try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonArray articles = reader.readObject().getJsonArray("articles");
			List<Article> articlesList = new ArrayList<>();

			for (JsonValue obj : articles) {
				JsonObject article = obj.asJsonObject();

				Article articleObj = Article.createArticle(article);
				newsviewMongoRepo.save(articleObj);
				articlesList.add(articleObj);
			}
			newsviewRepo.addArticles(articlesList);
			return Optional.ofNullable(articlesList);
		}
	}

	public Optional<JsonArray> stringToArticlesJsonArray(String jsonString) throws IOException {
		try (InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonArray articles = reader.readObject().getJsonArray("articles");
			return Optional.ofNullable(articles);
		}
	}

	public static String normaliseString(String s) {
		s = s.trim().toLowerCase().replace(" ", "+");
		return s;
	}

}
