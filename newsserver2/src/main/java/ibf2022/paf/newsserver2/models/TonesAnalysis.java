package ibf2022.paf.newsserver2.models;

import java.util.List;

import ibf2022.paf.newsserver2.models.submodels.tonesubmodels.DocumentTone;
import ibf2022.paf.newsserver2.models.submodels.tonesubmodels.SentenceTone;

public class TonesAnalysis {
	private DocumentTone documentTone;
	private List<SentenceTone> sentenceTones;

	public TonesAnalysis() {
	}

	public TonesAnalysis(DocumentTone documentTone, List<SentenceTone> sentenceTones) {
		this.documentTone = documentTone;
		this.sentenceTones = sentenceTones;
	}

	public DocumentTone getDocumentTone() {
		return this.documentTone;
	}

	public void setDocumentTone(DocumentTone documentTone) {
		this.documentTone = documentTone;
	}

	public List<SentenceTone> getSentenceTones() {
		return this.sentenceTones;
	}

	public void setSentenceTones(List<SentenceTone> sentenceTones) {
		this.sentenceTones = sentenceTones;
	}

}
