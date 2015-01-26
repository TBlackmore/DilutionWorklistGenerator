
public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test Script");
		
		
		//all of this information needs to be provided from the form/gui
		
		//source plate
		Plate testSourcePlate = new Plate(8,16,"Short matrix");
		//source plate array
		Plate[] testSourcePlates = new Plate[1];
		testSourcePlates[0] = testSourcePlate;
		
		
		//The type of prep plate to be used
		Plate testPrepPlate = new Plate(8,16,"500uL masterblock");
		
		//the type of target plate to be used
		Plate testTargetPlate = new Plate(16,32,"200 uL masterblock");
		
		//testSamples
		Sample sample1 = new Sample("sample1",testSourcePlate.getWell(1,1),100);
		
		//add test samples to a sample list
		SampleList testSampleList = new SampleList(testPrepPlate, testTargetPlate);
		testSampleList.addSample(sample1);
		
		
		Run testRun = new Run(testSampleList, testPrepPlate, testTargetPlate);
		
	}

}
