import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RunController {

	private ArrayList<Sample> samples;
	
	private Plate prepPlateType;
	private Plate targetPlateType;
	private Plate sourcePlateType;
	
	private ArrayList<Plate> prepPlates;
	private Plate targetPlate;
	private ArrayList<Plate> sourcePlates;
	
	private Sample currentSample;
	private boolean plateFound;
	private IO runIO;
	private PrintWriter out;
	
	public RunController(IO runIO, Plate prepPlateType, Plate targetPlateType, ArrayList<Sample> samples) {
		this.prepPlateType = prepPlateType;
		this.targetPlateType = targetPlateType;
		this.samples = samples;
		this.runIO = runIO;
		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			generateDilutions(currentSample);
		}
		
		arrangeDilutions(samples);
		//open output stream
		try {
			out = runIO.openOutputStream("test.txt");
		} catch (IOException e) {
			System.out.println("Error opening file: " + e.getMessage());
		}
		//output the commands to the file
		out.print("Hello");
		//Print out the sample info for each sample
		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			System.out.println(currentSample.getInfo());
		}
		//close the output stream
		runIO.closeOutputStream(out);
	}
	public Plate getPrepPlateType() {
		return this.prepPlateType;
	}
	
	public Plate getTargetPlateType() {
		return this.targetPlateType;
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
			nextSampleVol = Math.max(prepPlateType.getMinAspVol(), (currentDilution/targetDilutionFactor)*prepPlateType.getWellVol());
			
			// Update current dilution value
			currentDilution = currentDilution * ( 1.0 / (nextSampleVol / prepPlateType.getWellVol()));
			
			// Add new dilution to prepDilutions array List
			Dilution newPrepDilution = new Dilution(nextSampleVol, 
					prepPlateType.getWellVol() - nextSampleVol, currentDilution);
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
			//Check if the source plate already exists otherwise create it
			plateFound = false;
			for (int j = 0; j < sourcePlates.size(); j++) {
				if (s.getSourceName() == sourcePlates.get(j).getName() & 
						s.getSourceLabware() == sourcePlates.get(j).getLabware()) {
					plateFound = true;
				}
			}
			if (plateFound == true) {
				//FIX ME! need to contrust the new plat, but don't have the plate dimensions
				//search for "96" in labware name or create a list of labware somewhere..
				//sourcePlates.add(new Plate())
			}
			pd = s.getPrepDilutions();
			//Assign source well to the source well of the first prep or target dilution.
			if (pd.size() > 0) {
				
			} else {
				
			}
			
			for (int j = 0 ; j < pd.size(); j++) {
				
			}
			
		}
		// check if there are duplicate source plates with identical names but different labware
		
	}
}
