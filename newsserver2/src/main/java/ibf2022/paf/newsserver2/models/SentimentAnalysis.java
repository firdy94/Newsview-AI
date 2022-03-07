package ibf2022.paf.newsserver2.models;

import java.util.List;

import com.ibm.watson.natural_language_understanding.v1.model.DocumentSentimentResults;

import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Category;
import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Entity;
import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Keyword;
import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Target;

public class SentimentAnalysis {

	private List<Entity> entities;
	private List<Category> categories;
	private List<Keyword> keywords;
	private List<Target> targets;
	private DocumentSentimentResults sentiment;

	public SentimentAnalysis() {
	}

	public SentimentAnalysis(List<Entity> entities, List<Category> categories, List<Keyword> keywords,
			DocumentSentimentResults sentiment) {
		this.entities = entities;
		this.categories = categories;
		this.keywords = keywords;
		this.sentiment = sentiment;
	}

	public List<Entity> getEntities() {
		return this.entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Keyword> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public DocumentSentimentResults getSentiment() {
		return this.sentiment;
	}

	public void setSentiment(DocumentSentimentResults sentiment) {
		this.sentiment = sentiment;
	}

	public List<Target> getTargets() {
		return this.targets;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}

}
