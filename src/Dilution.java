
public class Dilution {
	private double sampleVol;
	private double bufferVol;
	private double stepDilutionFactor;
	private double totalDilutionFactor;
	private double remainingVol;
	
	public Dilution(double sampleVol, double bufferVol, double totalDilutionFactor) {
		this.sampleVol = sampleVol;
		this.bufferVol = bufferVol;
		this.stepDilutionFactor = (1/(sampleVol/(sampleVol + bufferVol)));
		this.totalDilutionFactor = totalDilutionFactor;
		this.remainingVol = sampleVol + bufferVol;
	}
	
	public double subtractVol(double subtractVol) {
		remainingVol = remainingVol - subtractVol;
		return remainingVol;
	}
	
	public String getDetails() {
		return("sampleVol = " + sampleVol + ", bufferVol = " + bufferVol +
				", stepDil = " + stepDilutionFactor + ", totalDil = " + totalDilutionFactor);
	}
	
	public double getSampleVol() {
		return sampleVol;
	}
	
	public double getBufferVol() {
		return bufferVol;
	}
}
