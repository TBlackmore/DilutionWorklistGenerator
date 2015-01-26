import java.util.ArrayList;

public class Sample {
	private String name;
	private SampleList sampleList;
	private Well source;
	private double targetDilutionFactor;
	private Dilution[] prepDilutions;
	private Dilution targetDilution;
	private Dilution[] serialDilutions;
	
	public Sample (String name, Well source, double targetDilutionFactor) {
		this.name = name;
		this.source = source;
		this.targetDilutionFactor = targetDilutionFactor;
		this.sampleList = sampleList;
		
	}
	
	public boolean setList(SampleList sampleList) {
		this.sampleList = sampleList;
		return true;
	}
}
