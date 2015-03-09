import java.util.ArrayList;
import java.lang.Math;
/**
 * @author Tim
 *
 */
public class Sample {
	private String name;
	private double targetDilutionFactor;
	private Dilution source;
	private ArrayList<Dilution> prepDilutions;
	private Dilution targetDilution;
	private Dilution[] serialDilutions;
	
	private double currentDilution = 1;
	
	public Sample (String name, double targetDilutionFactor) {
		this.name = name;
		this.targetDilutionFactor = targetDilutionFactor;
		//this.sampleList = sampleList;
		this.prepDilutions = new ArrayList<Dilution>();
		//System.out.println(currentSource);
		
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
	public String getInfo() {
		String info;
		info = "Sample Name: " + this.name + "\n";
		for (int i = 0 ; i < prepDilutions.size() ; i++) {
			info = info + "S: " + prepDilutions.get(i).getDetails() + "\n";
		}
		info = info + "T: " + targetDilution.getDetails();
		return info;
	}
}
