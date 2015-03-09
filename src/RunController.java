import java.util.ArrayList;

public class RunController {

	private ArrayList<Sample> samples;
	private Plate prepPlate;
	private Plate targetPlate;
	private Plate[] sourcePlates;
	private Sample currentSample;
	
	public RunController(Plate prepPlateType, Plate targetPlateType,
			Plate[] sourcePlates, ArrayList<Sample> samples) {
		this.prepPlate = prepPlateType;
		this.targetPlate = targetPlateType;
		this.samples = samples;

		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			generateDilutions(currentSample);
		}
		
		arrangeDilutions(samples);
		//Print out the sample info for each sample
		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			System.out.println(currentSample.getInfo());
		}
	}
	public Plate getPrepPlateType() {
		return this.prepPlate;
	}
	
	public Plate getTargetPlateType() {
		return this.targetPlate;
	}
	
	/**
	 * Check if the target dilution can be reached.
	 * If the target dilution cannot be reached create prep dilution(s)
	 * Then creates the target dilution
	 * 
	 * Takes a sample as an argument, and returns the sample with dilutions added
	 *
	 * @param sample
	 * @return sample
	 */
	public Sample generateDilutions (Sample sample) {
		
		//tests
		double nextSampleVol;
		double targetDilutionFactor = sample.getTargetDilutionFactor();
		double currentDilution = 1;
		Dilution targetDilution = sample.getTargetDilution();
		
		//Keep adding prep dilutions until the targetDilutionFactor can be reached in the target plate
		while ((targetPlate.getMaxDil() * currentDilution) < targetDilutionFactor) {
			
			// The sample volume is the required volume to get the target dilution, or the minimum aspiration volume
			nextSampleVol = Math.max(prepPlate.getMinAspVol(), (currentDilution/targetDilutionFactor)*prepPlate.getWellVol());
			
			// Update current dilution value
			currentDilution = currentDilution * ( 1.0 / (nextSampleVol / prepPlate.getWellVol()));
			
			// Add new dilution to prepDilutions array List
			Dilution newPrepDilution = new Dilution(nextSampleVol, 
					prepPlate.getWellVol() - nextSampleVol, currentDilution);
			sample.getPrepDilutions().add(newPrepDilution);
			
			//System.out.println(newPrepDilution.getDetails());
		}
		// create the final dilution into the target plate
		// final dilution will be neat if the target dilution could not be reached during the transfer step
		
		double targetDilStep = targetDilutionFactor/currentDilution;
		double targetSampleVol = targetPlate.getWellVol() / targetDilStep;
		currentDilution = currentDilution * targetDilStep;
		targetDilution = new Dilution(targetSampleVol, targetPlate.getWellVol() - targetSampleVol, currentDilution);
		sample.setTargetDilution(targetDilution);
		//System.out.println("Added target dilution for sample: " + sample.getName() + " currentDilution = " + currentDilution);
		//System.out.println(targetDilution.getDetails());
		//System.out.println("sample dilutions generated");
		return sample;
	}
	/**
	 * Assign the source well to each sample dilution
	 * iterate through every dilution of every sample and assign both a source and destination well
	 * this arrangement dilutes samples across a plate, until the transfer step is reached
	 * @param sampleList
	 */
	public void arrangeDilutions(ArrayList<Sample> samples) {
		
		ArrayList<Dilution> pd;
		for (int i = 0 ; i < samples.size(); i ++) {
			Sample s = samples.get(i);
			pd = s.getPrepDilutions();
			//Assign source well to the source well of the first prep or target dilution.
			if (pd.size() > 0) {
				
			} else {
				
			}
			
			for (int j = 0 ; j < pd.size(); j++) {
				
			}
		}
	}
}
