
public class Dilution {
	private double sampleVol;
	private double bufferVol;
	private double stepDilutionFactor;
	private double totalDilutionFactor;
	private Well well;
	private Well sourceWell;
	
	public Dilution(double sampleVol, double bufferVol, double totalDilutionFactor) {
		this.sampleVol = sampleVol;
		this.bufferVol = bufferVol;
		this.stepDilutionFactor = (1/(sampleVol/(sampleVol + bufferVol)));
		this.totalDilutionFactor = totalDilutionFactor;
	}
	
	public String getDetails() {
		return("sampleVol = " + sampleVol + ", bufferVol = " + bufferVol +
				", stepDil = " + stepDilutionFactor + ", totalDil = " + totalDilutionFactor);
	}
}
