package ibf2022.paf.newsserver2.models.submodels.tonesubmodels;

public class Tone {
	private double score;
	private String toneId;
	private String toneName;

	public Tone() {
	}

	public Tone(double score, String toneId, String toneName) {
		this.score = score;
		this.toneId = toneId;
		this.toneName = toneName;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getToneId() {
		return this.toneId;
	}

	public void setToneId(String toneId) {
		this.toneId = toneId;
	}

	public String getToneName() {
		return this.toneName;
	}

	public void setToneName(String toneName) {
		this.toneName = toneName;
	}

}
