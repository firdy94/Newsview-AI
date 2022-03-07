package ibf2022.paf.newsserver2.models.submodels.sentimentsubmodels;

public class Sentiment {

	private String label;
	private double score;

	public Sentiment() {
	}

	public Sentiment(String label, double score) {
		this.label = label;
		this.score = score;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
