import java.util.ArrayList;

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
	 * If the target dilution cannot be reached create another prep dilution
	 * Then creates the target dilution
	 * 
	 * Fix me: prep dilutions can overshoot target dilution when target requires more than max tartget step but less than a whole prep step
	 * 
	 * @param plate
	 * @return
	 */
	public double generateDilutions (Plate prepPlate, Plate targetPlate) {
		while ((targetPlate.getMaxDil() * currentDilution) <= targetDilutionFactor) {
			//create prep dilution
			currentDilution = currentDilution * prepPlate.getMaxDil();
			Dilution newPrepDilution = new Dilution(prepPlate.getMinAspVol(), 
					prepPlate.getWellVol() - prepPlate.getMinAspVol(), currentDilution);
			this.prepDilutions.add(newPrepDilution);
			System.out.println("Added prep dilutions for sample: " + name + " currentDilution = " + currentDilution);
			newPrepDilution.print();
		}
		//enough prep dilutions have been added so that the target dilution can be reached
		double targetDilStep = targetDilutionFactor/currentDilution;
		double targetSampleVol = targetPlate.getWellVol() / targetDilStep;
		currentDilution = currentDilution * targetDilStep;
		targetDilution = new Dilution(targetSampleVol, targetPlate.getWellVol() - targetSampleVol, currentDilution);
		System.out.println("Added target dilution for sample: " + name + " currentDilution = " + currentDilution);
		targetDilution.print();
		return 0;
	}
}
