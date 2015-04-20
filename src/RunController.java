import java.io.IOException;
import java.io.PrintWriter;
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
	private IO runIO;
	private PrintWriter out;
	

	public RunController(IO runIO, Plate prepPlateType, Plate targetPlateType, ArrayList<Sample> samples) {

		this.prepPlateType = prepPlateType;
		this.targetPlateType = targetPlateType;
		this.targetPlate = targetPlateType;
		this.samples = samples;
		this.runIO = runIO;
		for (int s = 0; s < samples.size(); s++) {
			currentSample = samples.get(s);
			generateDilutions(currentSample);
		}

		setSampleSources(samples);
		//arrangePrepDilutions(samples);
		arrangePrepDilutionsInRows();
		
		//open output stream
		try {
			out = runIO.openOutputStream("test.txt");
			System.out.println("test.txt opened");
		} catch (IOException e) {
			System.out.println("Error opening file: " + e.getMessage());
		}

		//Print out the sample info for each sample
		for (int i = 0; i < samples.size(); i++) {
			ArrayList<Dilution> pd = samples.get(i).getPrepDilutions();
			
			for (int j = 0; j < pd.size(); j++) {
				out.println(aspDilCmd(pd.get(j)));
				out.println(aspSampleCmd(pd.get(j)));
				out.println(dispenseBothCmd(pd.get(j)));
			}
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
	
	public ArrayList<Plate> getSourcePlates() {
		return sourcePlates;
	}

	public ArrayList<Plate> getPrepPlates() {
		return prepPlates;
	}

	
	/**
	 * Assign the source well to each sample dilution
	 *
	 * FIXME: Creates a new source plate for every sample
	 * Assumes all source plates are 96 well plates, with fixed vols and min asps
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
				System.out.println("Compare names: " + s.getSourceName()    + ", " + sourcePlates.get(j).getName()   );
				System.out.println("Compare labwares: " + s.getSourceLabware() + ", " + sourcePlates.get(j).getLabware());
				
				if (s.getSourceName().equals(sourcePlates.get(j).getName()) & 
						s.getSourceLabware().equals(sourcePlates.get(j).getLabware())) {
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
			//assign the sample to the source dilution
			s.getSource().setSample(s);
			
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
		// FIXME check each dilution in each plate has the correct sourcePlate and wellNumber set.
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
		dilution.setSample(sample);
		dilution.getSource().subtractVol(dilution.getSampleVol());
	}
	
	/**
	 * Creates preplates, arranges the prep dilutions, going left to right on the prep plates
	 * 
	 * @param samples
	 */
	/*public void arrangePrepDilutions(ArrayList<Sample> samples) {
		Sample s;
		ArrayList<Dilution> pd;
		int nextWell = 1;
		int recallWell;
		//create a prep plate
		int nextPlate = 1;
		prepPlates.add(new Plate(prepPlateType, prepPlateType.getName() + nextPlate));
		int totalWells = prepPlates.get(0).totalWells();
		//loop through each sample in the sample list
		for (int i = 0; i < samples.size(); i++) {
			s = samples.get(i);
			pd = s.getPrepDilutions();
			//if the next well in the plate already has a dilution then skip to the next empty well
			//could this lead to samples being spread over multiple rows?
			while (prepPlates.get((nextWell - 1)/prepPlateType.totalWells()).getDilution(1 + nextWell % prepPlateType.totalWells()) != null) {
				nextWell++;
			}
			recallWell = nextWell;
			//loop through each prep dilution in sample
			for (int j = 0; j < pd.size(); j++) {
				//if the nextWell is on the next plate add a new plate
				if ((nextWell - ((nextPlate - 1) * prepPlateType.totalWells())) > totalWells) {
					nextPlate++;
					prepPlates.add(new Plate(prepPlateType, prepPlateType.getName() + nextPlate));
				}
				System.out.println("nextWell = " + nextWell);
				//link the dilution to the plate and wellNumber
				prepPlates.get((nextWell - 1)/prepPlateType.totalWells()).setDilution(pd.get(j), 1 + nextWell % prepPlateType.totalWells());

				//move to the well to the right
				nextWell = nextWell + prepPlateType.getRows();
			}
			nextWell = recallWell + 1;
		}
		
	}*/
	
	public void arrangePrepDilutionsInRows() {
		Sample s;
		ArrayList<Dilution> pd;
		Plate currentPlate;
		Plate nextPlate;
		Plate recallPlate;
		Dilution currentDilution;
		int[] currentWell = {1, 1};
		int[] nextWell = {1, 1};
		int[] recallWell = {1,1};
		//Create the first prepPlate
		currentPlate = new Plate(prepPlateType, prepPlateType.getName() + (prepPlates.size() + 1));
		prepPlates.add(currentPlate);
		//Loop through each sample
		for (int i = 0; i < samples.size(); i++) {
			s = samples.get(i);
			pd = s.getPrepDilutions();
			//store the the next well down to come back to after going right
			recallWell = nextWell;
			recallPlate = currentPlate;
			//loop through each prep dilution
			for (int j = 0; j < pd.size(); j++) {
				currentDilution = pd.get(j);
				currentPlate.setDilution(currentDilution, nextWell);
				//if the current well is in the last column and there's another dilution create a new plate
				//FIXME: adds a new plate each time
				if (currentPlate.isLastCol(nextWell) && (pd.size() >= j)) {
					int currentPlateIndex = prepPlates.indexOf(currentPlate);
					if ((prepPlates.size() - 1) > currentPlateIndex) {
						currentPlate = prepPlates.get(currentPlateIndex + 1);
					} else {
						nextPlate = new Plate(prepPlateType, prepPlateType.getName() + (prepPlates.size() + 1));
						prepPlates.add(nextPlate);
						currentPlate = nextPlate;
					}
					//nextPlate = new Plate(prepPlateType, prepPlateType.getName() + (prepPlates.size() + 1));
					//prepPlates.add(nextPlate);
					//currentPlate = nextPlate;
				}
				//nextWell is one well to the right
				currentWell = nextWell;
				nextWell = currentPlate.nextRowColRight(currentWell);
			}
			//go back to before all the moves to the right
			currentPlate = recallPlate;
			currentWell = recallWell;
			//find the next available well and set it to nextWell
			nextWell = currentWell;
			System.out.println("nextWell = " + nextWell[0] + ", " + nextWell[1]);
			while (currentPlate.getDilution(nextWell) != null) {
				
				//if the last well in the plate is reached go to the next plate in the list
				if (currentPlate.isLastWell(nextWell)) {
					
					//if the next plate doesn't exist, create it
					int currentPlateIndex = prepPlates.indexOf(currentPlate);
					if ((prepPlates.size() - 1) > currentPlateIndex) {
						currentPlate = prepPlates.get(currentPlateIndex + 1);
					} else {
						nextPlate = new Plate(prepPlateType, prepPlateType.getName() + (prepPlates.size() + 1));
						prepPlates.add(nextPlate);
						currentPlate = nextPlate;
					}
				}
				//move nextWell down
				nextWell = currentPlate.nextRowColDown(nextWell);
				System.out.println("nextWell = " + nextWell[0] + ", " + nextWell[1]);
			}
		}
	}
	
	// FIXME
	// variables that need to be configurable but aren't yet
	private String diluentName = "Diluent";
	private String diluentLabware = "Trough 100ml";
	private double diluentUsed = 0.0;
	private int diluentTroughVol = 90000;
	private int diluentTroughCount = 1;
	private int diluentAspPos = 1;
	/**
	 * Get the command to aspirate diluent for the given dilution
	 * note: aspiration position in the trough is incremented in each call for the whole run
	 * @param d
	 * @return
	 */
	public String aspDilCmd (Dilution d) {
		String aspDil;		
		aspDil = "A;" + diluentName + diluentTroughCount + ";;" + diluentLabware + ";" + (diluentAspPos + 1) 
				+ ";;" + d.getBufferVol() + ";;;";
		//System.out.println(aspDil);
		diluentAspPos = (diluentAspPos + 1) % 8;
		diluentUsed = diluentUsed + d.getBufferVol();
		diluentTroughCount = 1 + (int) (diluentUsed / diluentTroughVol);
		return aspDil;
	}
	/**
	 * Get the command to aspirate the source sample for the given dilution
	 * @param d
	 * @return
	 */
	public String aspSampleCmd (Dilution d) {
		String aspSample;
		aspSample = "A;" + d.getSource().getPlate().getName() + ";;" + d.getSource().getPlate().getLabware() 
				+ ";" + d.getSource().getWellNum() + ";" + d.getSampleVol() + ";;;"; 
		return aspSample;
	}
	/**
	 * Get the command to dispense both the sample and buffer volume for the given dilution
	 * @param d
	 * @return
	 */
	public String dispenseBothCmd (Dilution d) {
		String dispBoth;
		dispBoth = "D;" + d.getPlate().getName() + ";;" + d.getPlate().getLabware()
				+ ";" + d.getWellNum() + ";;" + d.getBufferVol() + d.getSampleVol() + ";;;";
		return dispBoth;
	}
	
}
