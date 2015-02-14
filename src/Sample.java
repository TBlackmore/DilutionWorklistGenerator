import java.util.ArrayList;
import java.lang.Math;
/**
 * @author Tim
 *
 */
public class Sample {
	private String name;
	private SampleList sampleList;
	private double targetDilutionFactor;
	private ArrayList<Dilution> prepDilutions;
	private Dilution targetDilution;
	private Dilution[] serialDilutions;
	
	private Well currentSource;
	private double currentDilution = 1;
	
	public Sample (String name, Well source, double targetDilutionFactor) {
		this.name = name;
		this.currentSource = source;
		this.targetDilutionFactor = targetDilutionFactor;
		//this.sampleList = sampleList;
		this.prepDilutions = new ArrayList<Dilution>();
		
	}
	
	public boolean setList(SampleList sampleList) {
		this.sampleList = sampleList;
		return true;
	}
	
	public boolean setTargetDilution(Dilution targetDilution) {
		this.targetDilution = targetDilution;
		return true;
	}
	public double getTargetDilutionFactor() {
		return this.targetDilutionFactor;
	}
	
	public ArrayList<Dilution> getPrepDilutions() {
		return this.prepDilutions;
	}
	public Dilution getTargetDilution() {
		return this.targetDilution;
	}
	public String getName() {
		return this.name;
	}
	
}
