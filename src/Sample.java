import java.util.ArrayList;

public class Sample {
	private String name;
	private Well source;
	private double targetDilutionFactor;
	private Dilution[] prepDilutions;
	private Dilution targetDilution;
	private Dilution[] serialDilutions;
	
	public Sample (String name, Well source, double targetDilutionFactor) {
		this.name = name;
		this.source = source;
		this.targetDilutionFactor = targetDilutionFactor;
		
		
	}
}
