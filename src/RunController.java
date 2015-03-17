import java.util.ArrayList;

public class RunController {

	private ArrayList<Sample> samples;
	
	private Plate prepPlateType;
	private Plate targetPlateType;
	private Plate sourcePlateType;
	
	private ArrayList<Plate> prepPlates = new ArrayList<Plate>();
	private Plate targetPlate;
	private ArrayList<Plate> sourcePlates = new ArrayList<Plate>();
	
	private Sample currentSample;
	private Sample newSourceDilution;
	private Plate currentSourcePlate;
	private boolean plateFound;
	
	public RunController(Plate prepPlateType, Plate targetPlateType, ArrayList<Sample> samples) {
		this.prepPlateType = prepPlateType;
		this.targetPlateType = targetPlateType;
		this.targetPlate = targetPlateType;
		this.samples = samples;

		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			generateDilutions(currentSample);
		}
		setSampleSources(samples);
		arrangePrepDilutions(samples);
		//Print out the sample info for each sample
		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			System.out.println(currentSample.getInfo());
		}
	}
	public Plate getPrepPlateType() {
		return this.prepPlateType;
	}
	
	public Plate getTargetPlateType() {
		return this.targetPlateType;
	}
	
	/**
	 * Assign the source well to each sample dilution
	 *
	 * FIXME: Assumes all source plates are 96 well plates, with fixed vols and min asps
	 * 
	 * @param sampleList
	 */
	public void setSampleSources(ArrayList<Sample> samples) {
		
		//ArrayList<Dilution> pd;
		for (int i = 0 ; i < samples.size(); i ++) {
			Sample s = samples.get(i);
			//Check if the source plate already exists otherwise create it
			plateFound = false;
			for (int j = 0; j < sourcePlates.size(); j++) {
				if (s.getSourceName() == sourcePlates.get(j).getName() & 
						s.getSourceLabware() == sourcePlates.get(j).getLabware()) {
					plateFound = true;
					currentSourcePlate = sourcePlates.get(j);
				}
			}
			if (plateFound == false) {
				//FIXME all source plates are 96 wells of 150 uL and minAsp of 20, need to be able to define these values
				sourcePlates.add(new Plate(s.getSourceName(), s.getSourceLabware(), 150, 20));
				currentSourcePlate = sourcePlates.get(sourcePlates.size() - 1);
			}
			//Set the samples source dilution to the source position
			currentSourcePlate.setDilution(s.getSource(), s.getSourceWellNumber());
			
			//pd = s.getPrepDilutions();
		}
		// check if there are duplicate source plates with identical names but different labware
		for (int i = 0; i < sourcePlates.size(); i++) {
			for (int j = i + 1; j < sourcePlates.size(); j++) {
				if (sourcePlates.get(i).getName().equals(sourcePlates.get(j).getName()) & 
						sourcePlates.get(i).getLabware().equals(sourcePlates.get(j).getLabware())) {
					System.out.println("ERROR: Multiple source plates with match name and labware detected");
				}
			}
		}
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
		boolean sampleSourceAssigned;
		sampleSourceAssigned = false;
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
			
			// Create new dilution, 
			Dilution newPrepDilution = new Dilution(nextSampleVol, 
					prepPlateType.getWellVol() - nextSampleVol, currentDilution);
			// Assign source well
			assignSource(sampleSourceAssigned, newPrepDilution, sample);
			
			// add to prepDilutions array List
			sample.getPrepDilutions().add(newPrepDilution);
			//System.out.println(newPrepDilution.getDetails());
		}
		// create the final dilution into the target plate
		// final dilution will be neat if the target dilution could not be reached during the transfer step
		
		double targetDilStep = targetDilutionFactor/currentDilution;
		double targetSampleVol = targetPlate.getWellVol() / targetDilStep;
		currentDilution = currentDilution * targetDilStep;
		targetDilution = new Dilution(targetSampleVol, targetPlate.getWellVol() - targetSampleVol, currentDilution);
		assignSource(sampleSourceAssigned, targetDilution, sample);

		sample.setTargetDilution(targetDilution);

		//System.out.println("Added target dilution for sample: " + sample.getName() + " currentDilution = " + currentDilution);
		//System.out.println(targetDilution.getDetails());
		//System.out.println("sample dilutions generated");
		return sample;
	}
	
	/**
	 * Assigns the source of the dilution to another dilution object and substracts the required volume
	 * from the source dilution's currentvolume
	 * 
	 * FIXME: Should also check if the required volume for the dilution is available (although difficult for sample source)
	 * 
	 * @param sampleSourceAssigned
	 * @param dilution
	 * @param sample
	 */
	public void assignSource(Boolean sampleSourceAssigned, Dilution dilution, Sample sample) {
		if (sampleSourceAssigned == false) {
			dilution.setSource(sample.getSource());
			sampleSourceAssigned = true;
		} else {
			dilution.setSource(sample.getPrepDilutions().get(sample.getPrepDilutions().size() - 1));
		}
		dilution.getSource().subtractVol(dilution.getSampleVol());
	}
	
	/**
	 * Arrange the prep dilutions, left to right on the prep plates
	 * FIXME: doesn't assign dilutions in the first row or column
	 * @param samples
	 */
	public void arrangePrepDilutions(ArrayList<Sample> samples) {
		Sample s;
		ArrayList<Dilution> pd;
		int nextWell = 1;
		int recallWell;
		//create a prep plate
		int nextPlate = 1;
		prepPlates.add(new Plate(prepPlateType.getRowSize(), prepPlateType.getColumnSize(), 
				prepPlateType.getName() + nextPlate, prepPlateType.getLabware(),
				prepPlateType.getWellVol(), prepPlateType.getMinAspVol()));
		//loop through each sample in the sample list
		for (int i = 0; i < samples.size(); i++) {
			s = samples.get(i);
			pd = s.getPrepDilutions();
			//if the next well in the plate already has a dilution then skip to the next empty well
			//could this lead to samples being spread over multiple rows?
			while (prepPlates.get(nextWell/prepPlateType.totalWells()).getDilution(nextWell) != null) {
				nextWell++;
			}
			recallWell = nextWell;
			//loop through each prep dilution in sample
			for (int j = 0; j < pd.size(); j++) {
				System.out.println("nextWell = " + nextWell);
				prepPlates.get(nextWell/prepPlateType.totalWells()).setDilution(pd.get(j), nextWell);
				//move to the well to the right
				nextWell = nextWell + prepPlateType.getColumnSize();
			}
			nextWell = recallWell + 1;
		}
		
	}
	
}
