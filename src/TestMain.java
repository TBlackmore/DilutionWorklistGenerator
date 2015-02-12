
public class TestMain {
	
	public static double[] dilutionVols (double dilutionFactor, double totalVol) {
		double sampleVol = totalVol / dilutionFactor;
		double bufferVol = totalVol - sampleVol;
		double[] volumes = {sampleVol, bufferVol};
		return volumes;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//all of this information needs to be provided from the form/gui
		
		//source plate
		Plate testSourcePlate = new Plate(8,16,"Short matrix", 120, 20);
		//source plate array
		Plate[] testSourcePlates = new Plate[1];
		testSourcePlates[0] = testSourcePlate;
		
		
		//The type of prep plate to be used
		Plate testPrepPlate = new Plate(8,16,"500uL masterblock", 400, 20);
		
		//the type of target plate to be used
		Plate testTargetPlate = new Plate(16,32,"200 uL masterblock", 180, 20);
		
		//testSamples
		Sample sample1 = new Sample("sample1",testSourcePlate.getWell(1,1),6000); //Can't reach target dil during transfer
		Sample sample2 = new Sample("sample2",testSourcePlate.getWell(1,1),10); //Can't reach target dil during transfer on first step
		Sample sample3 = new Sample("sample3",testSourcePlate.getWell(1,1),9); //First step is target dil during transfer
		Sample sample4 = new Sample("sample4",testSourcePlate.getWell(1,1),1); // No dilution, to be run neat
		
		//add test samples to a sample list
		SampleList testSampleList = new SampleList(testPrepPlate, testTargetPlate);
		testSampleList.addSample(sample1);
		testSampleList.addSample(sample2);
		testSampleList.addSample(sample3);
		testSampleList.addSample(sample4);
		
		
		RunController testRun = new RunController(testSampleList, testPrepPlate, testTargetPlate);
		
		System.out.println("Test script completed?");
	}

}
