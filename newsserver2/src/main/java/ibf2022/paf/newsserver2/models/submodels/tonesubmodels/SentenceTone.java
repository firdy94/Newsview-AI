package ibf2022.paf.newsserver2.models.submodels.tonesubmodels;

import java.util.List;

import com.ibm.watson.tone_analyzer.v3.model.ToneScore;

public class SentenceTone {

	protected String text;
	protected List<ToneScore> tones;

	public SentenceTone() {
	}

	public SentenceTone(String text, List<ToneScore> tones) {
		this.text = text;
		this.tones = tones;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ToneScore> getTones() {
		return this.tones;
	}

	public void setTones(List<ToneScore> tones) {
		this.tones = tones;
	}
}