import java.util.ArrayList;

public class Main {
	
	public static double[] dilutionVols (double dilutionFactor, double totalVol) {
		double sampleVol = totalVol / dilutionFactor;
		double bufferVol = totalVol - sampleVol;
		double[] volumes = {sampleVol, bufferVol};
		return volumes;
	}
	
	public static double dilutionFactor (double sampleVol, double bufferVol) {
		return 1 / (sampleVol / (sampleVol + bufferVol));
	}
	
	
	public static void main(String[] args) {

		//all of this information needs to be provided from the form/gui
		//source plate
		Plate testSourcePlate = new Plate(8,16,"testSource","Short matrix", 120, 20);
		//source plate array
		Plate[] testSourcePlates = new Plate[1];
		testSourcePlates[0] = testSourcePlate;
		
		
		//The type of prep plate to be used
		Plate testPrepPlate = new Plate(8,12,"testPrep","500uL masterblock", 200, 80);
		System.out.println("testPrepPlate.wellCol(9) " + testPrepPlate.wellCol(9) + ", testPrepPlate.wellRow(9) " + testPrepPlate.wellRow(9));
		
		//the type of target plate to be used
		Plate testTargetPlate = new Plate(16,32,"testTarget","200 uL masterblock", 180, 20);
		
		//testSamples
		//multiple dilution steps required
		Sample sample1 = new Sample(1,"sample1",testSourcePlate.getName(),testSourcePlate.getLabware(),"sample",1,60000000,2,1.5,2,2,true);
		//Can't reach target dil during transfer on first step
		Sample sample2 = new Sample(1,"sample2",testSourcePlate.getName(),testSourcePlate.getLabware(),"sample",1,10  ,2,1.5,2,2,true); 
		//First step is target dil during transfer
		Sample sample3 = new Sample(1,"sample3",testSourcePlate.getName(),testSourcePlate.getLabware(),"sample",1,9   ,2,1.5,2,2,true);  
		// No dilution, to be run neat
		Sample sample4 = new Sample(1,"sample4",testSourcePlate.getName(),testSourcePlate.getLabware(),"sample",1,1   ,2,1.5,2,2,true);  
		
		ArrayList<Sample> testSamples = new ArrayList<Sample>();
		testSamples.add(sample1);
		testSamples.add(sample2);
		testSamples.add(sample3);
		testSamples.add(sample4);
		
		IO runIO = new IO();
		ArrayList<Sample> importedSamples = runIO.openFile("example_input.csv");
		
		RunController testRun = new RunController(testPrepPlate, testTargetPlate, testSamples);
		System.out.println("Test script completed?");
	}

}
