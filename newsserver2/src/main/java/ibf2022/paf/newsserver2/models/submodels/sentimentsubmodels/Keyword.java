package ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels;

import com.ibm.watson.natural_language_understanding.v1.model.EmotionScores;
import com.ibm.watson.natural_language_understanding.v1.model.FeatureSentimentResults;

public class Keyword {

	private Double relevance;
	private String text;
	private EmotionScores emotion;
	private FeatureSentimentResults score;

	public Keyword() {
	}

	public Keyword(Double relevance, String text, EmotionScores emotion, FeatureSentimentResults score) {
		this.relevance = relevance;
		this.text = text;
		this.emotion = emotion;
		this.score = score;
	}

	public double getRelevance() {
		return this.relevance;
	}

	public void setRelevance(double relevance) {
		this.relevance = relevance;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public EmotionScores getEmotion() {
		return this.emotion;
	}

	public void setEmotion(EmotionScores emotion) {
		this.emotion = emotion;
	}

	public FeatureSentimentResults getScore() {
		return this.score;
	}

	public void setScore(FeatureSentimentResults score) {
		this.score = score;
	}

}
