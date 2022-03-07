package ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels;

import com.ibm.watson.natural_language_understanding.v1.model.EmotionScores;
import com.ibm.watson.natural_language_understanding.v1.model.FeatureSentimentResults;

public class Entity {

	private String type;
	private String text;
	private Double confidence;
	private EmotionScores emotion;
	private FeatureSentimentResults results;

	public Entity() {
	}

	public Entity(String type, String text, Double confidence, EmotionScores emotion, FeatureSentimentResults results) {
		this.type = type;
		this.text = text;
		this.confidence = confidence;
		this.emotion = emotion;
		this.results = results;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getConfidence() {
		return this.confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public EmotionScores getEmotion() {
		return this.emotion;
	}

	public void setEmotion(EmotionScores emotion) {
		this.emotion = emotion;
	}

	public FeatureSentimentResults getResults() {
		return this.results;
	}

	public void setResults(FeatureSentimentResults results) {
		this.results = results;
	}

}
