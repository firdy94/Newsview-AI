package ibf2022.paf.newsserver2.models.submodels.tonesubmodels;

import java.util.List;

import com.ibm.watson.tone_analyzer.v3.model.ToneScore;

public class DocumentTone {

	List<ToneScore> toneScores;

	public DocumentTone() {
	}

	public DocumentTone(List<ToneScore> toneScores) {
		this.toneScores = toneScores;
	}

	public List<ToneScore> getToneScores() {
		return this.toneScores;
	}

	public void setToneScores(List<ToneScore> toneScores) {
		this.toneScores = toneScores;
	}
}
