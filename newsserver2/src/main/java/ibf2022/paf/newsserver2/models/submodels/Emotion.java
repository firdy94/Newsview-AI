package ibf2022.paf.newsserver2.models.submodels;

public class Emotion {
	private double anger;
	private double disgust;
	private double fear;
	private double joy;
	private double sadness;

	public Emotion() {
	}

	public Emotion(double anger, double disgust, double fear, double joy, double sadness) {
		this.anger = anger;
		this.disgust = disgust;
		this.fear = fear;
		this.joy = joy;
		this.sadness = sadness;
	}

	public double getAnger() {
		return this.anger;
	}

	public void setAnger(double anger) {
		this.anger = anger;
	}

	public double getDisgust() {
		return this.disgust;
	}

	public void setDisgust(double disgust) {
		this.disgust = disgust;
	}

	public double getFear() {
		return this.fear;
	}

	public void setFear(double fear) {
		this.fear = fear;
	}

	public double getJoy() {
		return this.joy;
	}

	public void setJoy(double joy) {
		this.joy = joy;
	}

	public double getSadness() {
		return this.sadness;
	}

	public void setSadness(double sadness) {
		this.sadness = sadness;
	}

}
