
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
				", stepDil = " + stepDilutionFactor + ", totalDil = " + totalDilutionFactor
				+ ", sourceWell = " + sourceWell + ", well = " + well);
	}
	
	public double getSampleVol() {
		return sampleVol;
	}
	
	public double getBufferVol() {
		return bufferVol;
	}
	public void setWell(Well well) {
		if (well.setDilution(this)) {
			this.well = well;			
		};
	}
	public void setSourceWell(Well sourceWell) {
		this.sourceWell = sourceWell;
	}
}
