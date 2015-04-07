import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;


public class RunControllerTest {
	
	@Test
	public void testGenerateDilutions() {
		//Big prep plate, little target plate, little source plate
		//source plate
		Plate testSourcePlate = new Plate(8,16,"testSource","Short matrix", 120, 20);
		
		//source plate array
		Plate[] testSourcePlates = new Plate[1];
		testSourcePlates[0] = testSourcePlate;
		
		//The type of prep plate to be used
		Plate testPrepPlate = new Plate(8,12,"testPrep","500uL masterblock", 400, 20);
		
		//the type of target plate to be used
		Plate testTargetPlate = new Plate(16,32,"testTarget","200 uL masterblock", 180, 20);
		
		
		
		// Can't reach target dil during transfer
		//multiple dilution steps required
		Sample sample1 = new Sample(1,"sample1",testSourcePlate.getName(),testSourcePlate.getLabware(),"sample",1,6000,2,1.5,2,2,true);
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
		
		RunController testRun = new RunController(runIO, testPrepPlate, testTargetPlate, testSamples);
		// Define expected volumes for the given samples
		double[][][] expectedVols = new double[][][] {
				{{20,380},{20,380},{26.666,373.333},{180,0}},
				{{40,360},{180,0}},
				{{20,160}},
				{{180,0}},
				};
		// Check the generated dilution volumes match the expected values to 0.001
		// Loop through and check each prep dilution
		// Then check the final target dilution
		for (int s = 0 ; s < expectedVols.length - 1; s++) {			
			for (int i = 0 ; i < expectedVols[s].length - 1; i++) {
				//System.out.println(expectedVols[s][i][0] + " " + testSamples.get(s).getPrepDilutions().get(i).getSampleVol());
				assertEquals(expectedVols[s][i][0], testSamples.get(s).getPrepDilutions().get(i).getSampleVol(), 0.001);
				//System.out.println(expectedVols[s][i][1] + " " + testSamples.get(s).getPrepDilutions().get(i).getBufferVol());
				assertEquals(expectedVols[s][i][1], testSamples.get(s).getPrepDilutions().get(i).getBufferVol(), 0.001);
			}
			//System.out.println("Target Sample " + expectedVols[s][expectedVols[s].length - 1][0] + " " + testSamples.get(s).getTargetDilution().getSampleVol());
			assertEquals(expectedVols[s][expectedVols[s].length - 1][0], testSamples.get(s).getTargetDilution().getSampleVol(), 0.001);
			//System.out.println("Target Buffer " + expectedVols[s][expectedVols[s].length - 1][1] + " " + testSamples.get(s).getTargetDilution().getBufferVol());
			assertEquals(expectedVols[s][expectedVols[s].length - 1][1], testSamples.get(s).getTargetDilution().getBufferVol(), 0.001);
		}
		
	}
	
	// Testing for sample that has more dilutions than can be arranged across a plate
	//@Test
	public void testArrangePrepDilutions() {
		//Big prep plate, little target plate, little source plate
				//source plate
				Plate testSourcePlate = new Plate(8,16,"testSource","Short matrix", 120, 20);
				
				//Prep plate only allows for small dilution steps
				Plate testPrepPlate = new Plate(8,12,"testPrep","500uL masterblock", 400, 20);
				
				//the type of target plate to be used
				Plate testTargetPlate = new Plate(16,32,"testTarget","200 uL masterblock", 180, 20);
				
				// Can't reach target dil during transfer
				Sample sample1 = new Sample(1,"sample1",testSourcePlate.getName(),testSourcePlate.getLabware(),"sample",1,60000000,2,1.5,2,2,true);
				
				ArrayList<Sample> testSamples = new ArrayList<Sample>();
				testSamples.add(sample1);
				IO runIO = new IO();
				
				RunController testRun = new RunController(runIO, testPrepPlate, testTargetPlate, testSamples);
				assertTrue(false);
	}

}
