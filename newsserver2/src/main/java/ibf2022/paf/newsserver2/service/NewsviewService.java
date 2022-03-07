package ibf2022.paf.newsserver2.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.paf.newsserver2.models.Article;
import ibf2022.paf.newsserver2.models.SentimentAnalysis;
import ibf2022.paf.newsserver2.models.TonesAnalysis;
import ibf2022.paf.newsserver2.repositories.NewsviewMongoRepository;
import ibf2022.paf.newsserver2.repositories.NewsviewRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class NewsviewService {

	@Autowired
	NewsviewMongoRepository newsviewMongoRepo;

	@Autowired
	SentimentService sentimentSvc;

	@Autowired
	ToneAnalyzeService toneAnalyzeSvc;

	@Autowired
	NewsviewRepository newsviewRepo;

	public String articleResp(Article article) {
		ObjectMapper objectMapper = new ObjectMapper();
		String resp = "";
		try {
			resp = objectMapper.writeValueAsString(article);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return resp;
		}
		return resp;
	}

	public String articleArrayResp(List<Article[]> articlesArray) {
		ObjectMapper objectMapper = new ObjectMapper();
		String resp = "";
		try {
			resp = objectMapper.writeValueAsString(articlesArray);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return resp;
		}
		return resp;
	}

	public Article articleSetSentimentAndTone(String id) {
		Article article = newsviewMongoRepo.findItemByUuid(id).get();
		String url = article.getUrlPath();
		// AnalysisResults results = sentimentSvc.getSentiment(url);
		Optional<SentimentAnalysis> sentAnalysis = Optional.empty();
		Optional<TonesAnalysis> tonesAnalysis = Optional.empty();

		if (sentimentSvc.getSentiment(url).isPresent()) {
			sentAnalysis = sentimentSvc.parseAnalysis(sentimentSvc.getSentiment(url).get());
		}
		if (toneAnalyzeSvc.getTone(url).isPresent()) {
			tonesAnalysis = toneAnalyzeSvc.parseToneAnalysis(toneAnalyzeSvc.getTone(url).get());
		}
		if (sentAnalysis.isPresent()) {
			article.setSentimentAnalysis(sentAnalysis.get());
		}
		if (tonesAnalysis.isPresent()) {
			article.setTonesAnalysis(tonesAnalysis.get());
		}
		newsviewMongoRepo.deleteById(url);
		newsviewMongoRepo.save(article);

		return article;
	}

	public void saveUser(String header) throws IOException {
		String token = header.split(" ")[1];
		String[] split_string = token.split("\\.");
		// String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		// String base64EncodedSignature = split_string[2];

		String body = new String(Base64.getDecoder().decode(base64EncodedBody));
		try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonObject data = reader.readObject();
			String name = data.getString("http://newsview/name");
			String email = data.getString("http://newsview/email");
			String nickname = data.getString("http://newsview/nickname");
			newsviewRepo.setUser(name, email, nickname);
		}
	}

	public String getUserEmail(String header) throws IOException {
		String token = header.split(" ")[1];
		String[] split_string = token.split("\\.");
		// String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		// String base64EncodedSignature = split_string[2];

		String body = new String(Base64.getDecoder().decode(base64EncodedBody));
		try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonObject data = reader.readObject();
			String email = data.getString("http://newsview/email");
			return email;
		}
	}
}
