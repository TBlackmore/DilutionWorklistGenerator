import java.util.ArrayList;

public class RunController {
	
	
	private SampleList sampleList;
	private Sample currentSample;
	private Plate prepPlateType;
	private Plate targetPlateType;
	
	//Should these be arrays or array lists?
	
	//private ArrayList<Plate> sourcePlates;
	//private ArrayList<Plate> prepPlates;
	//private ArrayList<Plate> targetPlates;
	
	public RunController(SampleList sampleList, Plate prepPlateType, Plate targetPlateType) {
		this.sampleList = sampleList;
		this.prepPlateType = prepPlateType;
		this.targetPlateType = targetPlateType;
		this.sampleList.setRunController(this);
		//Maybe all of the source plate details should just be in with the sample list
		//this.sourcePlates = sourcePlates;
		//this.prepPlates = new ArrayList<Plate>();
		//this.targetPlates[0] = targetPlates;
		
		for (int s = 0; s < sampleList.getAllSamples().size(); s++) {
			currentSample = sampleList.getAllSamples().get(s);
			currentSample.generateDilutions(prepPlateType, targetPlateType);
			
			//while (currentSample.getTargetDilutionFactor() < currentSample.addPrepDilution(prepPlateType)) {
			//	System.out.println("PrepDilution added for sample " + currentSample.getName());
			//}
		}
	}
	
	public Plate getPrepPlateType() {
		return this.prepPlateType;
	}
	
	public Plate getTargetPlateType() {
		return this.targetPlateType;
	}
}
