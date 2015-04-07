
public class Dilution {
	private double sampleVol;
	private double bufferVol;
	private double stepDilutionFactor;
	private double totalDilutionFactor;
	private double remainingVol;
	private Sample sample;
	private Dilution source;
	private Plate plate;
	private int wellNum;
	
	
	public Dilution(double totalDilutionFactor) {
		this.totalDilutionFactor = totalDilutionFactor;
	}
	
	public Dilution(double sampleVol, double bufferVol, double totalDilutionFactor) {
		this(totalDilutionFactor);
		this.sampleVol = sampleVol;
		this.bufferVol = bufferVol;
		this.stepDilutionFactor = (1/(sampleVol/(sampleVol + bufferVol)));
		this.remainingVol = sampleVol + bufferVol;
	}
	
	public Dilution(double sampleVol, double bufferVol, double totalDilutionFactor, Plate plate, int wellNum) {
		this(sampleVol, bufferVol, totalDilutionFactor);
		this.plate = plate;
		this.wellNum = wellNum;
	}
	
	public double subtractVol(double subtractVol) {
		remainingVol = remainingVol - subtractVol;
		return remainingVol;
	}
	
	public String getDetails() {
		return("sampleVol = " + sampleVol + ", bufferVol = " + bufferVol +
				", stepDil = " + stepDilutionFactor + ", totalDil = " + totalDilutionFactor);
	}
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	public Sample getSample() {
		return this.sample;
	}
	public Plate getPlate() {
		return plate;
	}

	public void setPlate(Plate plate) {
		this.plate = plate;
	}

	public int getWellNum() {
		return wellNum;
	}

	public void setWellNum(int wellNum) {
		this.wellNum = wellNum;
	}

	public double getSampleVol() {
		return sampleVol;
	}
	
	public double getBufferVol() {
		return bufferVol;
	}
	
	public Dilution getSource() {
		return this.source;
	}
	public void setSource(Dilution source) {
		this.source = source;
	}
}
