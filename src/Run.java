import java.util.ArrayList;

public class Run {
	
	
	private SampleList sampleList;
	private Plate prepPlateType;
	private Plate targetPlateType;
	
	//Should these be arrays or array lists?
	
	//private ArrayList<Plate> sourcePlates;
	//private ArrayList<Plate> prepPlates;
	//private ArrayList<Plate> targetPlates;
	
	public Run(SampleList sampleList, Plate prepPlateType, Plate targetPlateType) {
		this.sampleList = sampleList;
		this.prepPlateType = prepPlateType;
		this.targetPlateType = targetPlateType;
		
		//Maybe all of the source plate details should just be in with the sample list
		//this.sourcePlates = sourcePlates;
		//this.prepPlates = new ArrayList<Plate>();
		//this.targetPlates[0] = targetPlates;
		
		for (int s = 0; s < sampleList.getAllSamples().size(); s++) {
			sampleList.getAllSamples().get(s);
		}
	}
}
