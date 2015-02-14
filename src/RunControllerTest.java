import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;


public class RunControllerTest {
	
	@Test
	public void testGenerateDilutions() {
		//Big prep plate, little target plate, little source plate
		//source plate
		Plate testSourcePlate = new Plate(8,16,"Short matrix", 120, 20);
		
		//source plate array
		Plate[] testSourcePlates = new Plate[1];
		testSourcePlates[0] = testSourcePlate;
		
		//The type of prep plate to be used
		Plate testPrepPlate = new Plate(8,16,"500uL masterblock", 400, 20);
		
		//the type of target plate to be used
		Plate testTargetPlate = new Plate(16,32,"200 uL masterblock", 180, 20);
		
		// Can't reach target dil during transfer
		Sample sample1 = new Sample("sample1",testSourcePlate.getWell(1,1),6000); 
		// Can't reach target dil during transfer on first step
		Sample sample2 = new Sample("sample2",testSourcePlate.getWell(1,1),10);
		// First step is target dil during transfer
		Sample sample3 = new Sample("sample3",testSourcePlate.getWell(1,1),9); 
		// No dilution, to be run neat
		Sample sample4 = new Sample("sample4",testSourcePlate.getWell(1,1),1); 
		
		//add test samples to a sample list
		SampleList testSampleList = new SampleList(testPrepPlate, testTargetPlate);
		testSampleList.addSample(sample1);
		testSampleList.addSample(sample2);
		testSampleList.addSample(sample3);
		testSampleList.addSample(sample4);
		
		RunController testRun = new RunController(testSampleList, testPrepPlate, testTargetPlate);
		double[][][] expectedVols = new double[][][] {
				{{20,380},{20,380},{26.666,373.333},{180,0}},
				{{40,360},{180,0}},
				{{20,160}},
				{{180,0}},
				};
		for (int s = 0 ; s < expectedVols.length - 1; s++) {			
			for (int i = 0 ; i < expectedVols[s].length - 1; i++) {
				assertEquals(expectedVols[s][i][0], testSampleList.getAllSamples().get(s).getPrepDilutions().get(i).getSampleVol(), 0.001);
				assertEquals(expectedVols[s][i][1], testSampleList.getAllSamples().get(s).getPrepDilutions().get(i).getBufferVol(), 0.001);
			}
		}
		System.out.println("Breakpoint");
	}

}
