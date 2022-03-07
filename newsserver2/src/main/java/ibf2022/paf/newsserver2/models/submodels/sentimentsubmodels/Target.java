package ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels;

import com.ibm.watson.natural_language_understanding.v1.model.EmotionScores;

public class Target {

	private String text;
	private EmotionScores emotion;

	public Target() {
	}

	public Target(String text, EmotionScores emotion) {
		this.text = text;
		this.emotion = emotion;
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

}
