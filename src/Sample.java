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
	
	public double getTargetDilutionFactor() {
		return this.targetDilutionFactor;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check if the target dilution can be reached.
	 * If the target dilution cannot be reached create prep dilution(s)
	 * Then creates the target dilution
	 * 
	 *
	 * @param plate
	 * @return
	 */
	public double generateDilutions (Plate prepPlate, Plate targetPlate) {
		
		//tests
		double nextSampleVol;
		//Keep adding prep dilutions until the targetDilutionFactor can be reached in the target plate
		while ((targetPlate.getMaxDil() * currentDilution) < targetDilutionFactor) {
			
			// The sample volume is the required volume to get the target dilution, or the minimum aspiration volume
			nextSampleVol = Math.max(prepPlate.getMinAspVol(), (currentDilution/targetDilutionFactor)*prepPlate.getWellVol());
			
			// Update current dilution value
			currentDilution = currentDilution * ( 1.0 / (nextSampleVol / prepPlate.getWellVol()));
			
			// Add new dilution to prepDilutions array List
			Dilution newPrepDilution = new Dilution(nextSampleVol, 
					prepPlate.getWellVol() - nextSampleVol, currentDilution);
			this.prepDilutions.add(newPrepDilution);
			
			System.out.println(newPrepDilution.getDetails());
		}
		// create the final dilution into the target plate
		// final dilution will be neat if the target dilution could not be reached during the transfer step
		
		double targetDilStep = targetDilutionFactor/currentDilution;
		double targetSampleVol = targetPlate.getWellVol() / targetDilStep;
		currentDilution = currentDilution * targetDilStep;
		targetDilution = new Dilution(targetSampleVol, targetPlate.getWellVol() - targetSampleVol, currentDilution);
		System.out.println("Added target dilution for sample: " + name + " currentDilution = " + currentDilution);
		System.out.println(targetDilution.getDetails());
		System.out.println("sample dilutions generated");
		return 0;
	}
}
