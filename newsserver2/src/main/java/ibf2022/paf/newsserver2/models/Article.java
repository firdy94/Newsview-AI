package ibf2022.paf.newsserver2.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ibf2022.paf.newsserver2.service.ArticleService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Document(collection = "articles")
public class Article {

	@Id
	private String urlPath;
	private String id;
	private String website;
	private String author;
	private String title;
	private String description;
	private String imagePath;
	private String publishedDate;
	private SentimentAnalysis sentimentAnalysis;
	private TonesAnalysis tonesAnalysis;

	public Article() {
	}

	public Article(String website, String author, String title, String description, String urlPath, String imagePath,
			String publishedDate) {

		this.id = UUID.randomUUID().toString();
		this.website = website;
		this.author = author;
		this.title = title;
		this.description = description;
		this.urlPath = urlPath;
		this.imagePath = imagePath;
		this.publishedDate = publishedDate;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlPath() {
		return this.urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getPublishedDate() {
		return this.publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public SentimentAnalysis getSentimentAnalysis() {
		return this.sentimentAnalysis;
	}

	public void setSentimentAnalysis(SentimentAnalysis sentimentAnalysis) {
		this.sentimentAnalysis = sentimentAnalysis;
	}

	public TonesAnalysis getTonesAnalysis() {
		return this.tonesAnalysis;
	}

	public void setTonesAnalysis(TonesAnalysis tonesAnalysis) {
		this.tonesAnalysis = tonesAnalysis;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static String normaliseString(String s) {
		if (s.equals(null) || s.equals("")) {
			return "not found";
		} else {
			s = s.replaceAll("(\\\\r\\\\n)", "\r\n");
			s = s.replaceAll("(\\\\n)", "\n");
			return s.trim().replaceAll("\"", "");
		}
	}

	public static Article createArticle(JsonObject article) {
		ArticleService articleSvc = new ArticleService();
		String name = Article.normaliseString(article.getJsonObject("source").get("name").toString());
		String author = Article.normaliseString(article.get("author").toString());
		String title = Article.normaliseString(article.get("title").toString());
		String url = Article.normaliseString(article.get("url").toString());
		String description = "";
		try {
			if (articleSvc.getArticleBody(url).isPresent()) {
				description = Article.normaliseString(articleSvc.getArticleBody(url).get());
			}
		} catch (Exception e) {
			description = Article.normaliseString(article.get("content").toString().split("… ")[0]);
		}
		String urlImage = Article.normaliseString(article.get("urlToImage").toString());
		String publishedAt = Article.normaliseString(article.get("publishedAt").toString());
		return new Article(name, author, title, description, url, urlImage, publishedAt);
	}

	public static JsonArray createJsonObject(List<Article> articles) {
		JsonArrayBuilder jsonArrayBuild = Json.createArrayBuilder();

		for (Article article : articles) {
			JsonObject obj = Json.createObjectBuilder()
					.add("website", article.getWebsite())
					.add("title", article.getAuthor())
					.add("title", article.getTitle())
					.add("description", article.getDescription())
					.add("urlPath", article.getUrlPath())
					.add("imagePath", article.getImagePath())
					.add("publishedDate", article.getPublishedDate())
					.build();

			jsonArrayBuild.add(obj);
		}

		return jsonArrayBuild.build();

	}

}
