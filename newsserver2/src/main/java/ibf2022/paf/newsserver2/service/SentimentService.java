package ibf2022.paf.newsserver2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.natural_language_understanding.v1.model.DocumentSentimentResults;
import com.ibm.watson.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.natural_language_understanding.v1.model.Features;
import com.ibm.watson.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.natural_language_understanding.v1.model.SentimentOptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.paf.newsserver2.models.SentimentAnalysis;

import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Category;
import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Entity;
import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Keyword;
import ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels.Target;
import ibf2022.paf.newsserver2.repositories.NewsviewMongoRepository;

@Service
public class SentimentService {

	@Autowired
	NewsviewMongoRepository newsviewMongoRepo;

	public Optional<AnalysisResults> getSentiment(String url) {
		System.out.println("running");
		IamAuthenticator authenticator = new IamAuthenticator.Builder()
				.apikey(System.getenv("ibmapikey"))
				.build();
		System.out.println(authenticator.toString());

		NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding(
				System.getenv("ibmversion"),
				authenticator);

		System.out.println(naturalLanguageUnderstanding.toString());

		naturalLanguageUnderstanding.setServiceUrl(System.getenv("ibmurl"));

		SentimentOptions sentiment = new SentimentOptions.Builder()
				.build();

		CategoriesOptions categories = new CategoriesOptions.Builder()
				.limit(3)
				.build();

		EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
				.emotion(true)
				.sentiment(true)
				.limit(2)
				.build();

		KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
				.emotion(true)
				.sentiment(true)
				.limit(2)
				.build();

		Features features = new Features.Builder()
				.categories(categories)
				.entities(entitiesOptions)
				.sentiment(sentiment)
				.keywords(keywordsOptions)
				.build();
		System.out.println("After Features");
		AnalyzeOptions parameters = new AnalyzeOptions.Builder()
				.url(url)
				.features(features)
				.build();
		System.out.println("after analyze");
		AnalysisResults response = naturalLanguageUnderstanding
				.analyze(parameters)
				.execute()
				.getResult();

		return Optional.ofNullable(response);
	}

	public Optional<SentimentAnalysis> parseAnalysis(AnalysisResults results) {

		Optional<List<Category>> catResults = Optional.ofNullable(results.getCategories().stream()
				// .filter(p -> p.getLastName().equals("l1"))
				.map(p -> new Category(p.getLabel(), p.getScore()))
				.collect(Collectors.toList()));
		Optional<List<Entity>> EntResults = Optional.ofNullable(results.getEntities().stream()
				// .filter(p -> p.getLastName().equals("l1"))
				.map(p -> new Entity(p.getType(), p.getText(), p.getConfidence(),
						p.getEmotion(), p.getSentiment()))
				.collect(Collectors.toList()));

		Optional<List<Keyword>> KeyResults = Optional.ofNullable(results.getKeywords().stream()
				.map(p -> new Keyword(p.getRelevance(), p.getText(), p.getEmotion(),
						p.getSentiment()))
				.collect(Collectors.toList()));
		Optional<DocumentSentimentResults> SentResults = Optional.ofNullable(results.getSentiment().getDocument());
		SentimentAnalysis sAnalysis = new SentimentAnalysis();
		if (catResults.isPresent()) {
			sAnalysis.setCategories(catResults.get());
		}
		if (EntResults.isPresent()) {
			sAnalysis.setEntities(EntResults.get());
		}
		if (KeyResults.isPresent()) {
			sAnalysis.setKeywords(KeyResults.get());
		}
		if (SentResults.isPresent()) {
			sAnalysis.setSentiment(SentResults.get());
		}

		if (results.getEmotion() != null) {
			Optional<List<Target>> emotionResults = Optional.ofNullable(results.getEmotion().getTargets().stream()
					.map(p -> new Target(p.getText(), p.getEmotion())).collect(Collectors.toList()));

			if (emotionResults.isPresent()) {
				sAnalysis.setTargets(emotionResults.get());
			}
		}

		return Optional.ofNullable(sAnalysis);
	}

	public AnalysisResults getSearchForSentiment(String url, String query) {
		System.out.println("running");
		IamAuthenticator authenticator = new IamAuthenticator.Builder()
				.apikey(System.getenv("ibmapikey"))
				.build();
		System.out.println(authenticator.toString());

		NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding(
				System.getenv("ibmversion"),
				authenticator);

		System.out.println(naturalLanguageUnderstanding.toString());

		naturalLanguageUnderstanding.setServiceUrl(System.getenv("ibmurl"));

		List<String> targets = new ArrayList<>();
		targets.add(query);

		SentimentOptions sentiment = new SentimentOptions.Builder()
				.targets(targets)
				.build();

		CategoriesOptions categories = new CategoriesOptions.Builder()
				.limit(3)
				.build();

		EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
				.emotion(true)
				.sentiment(true)
				.limit(2)
				.build();

		KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
				.emotion(true)
				.sentiment(true)
				.limit(2)
				.build();

		Features features = new Features.Builder()
				.categories(categories)
				.entities(entitiesOptions)
				.sentiment(sentiment)
				.keywords(keywordsOptions)
				.build();
		System.out.println("After Features");
		AnalyzeOptions parameters = new AnalyzeOptions.Builder()
				.url(url)
				.features(features)
				.build();
		System.out.println("after analyze");
		AnalysisResults response = naturalLanguageUnderstanding
				.analyze(parameters)
				.execute()
				.getResult();

		System.out.println(response);
		return (response);
	}

}
