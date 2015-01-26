import java.util.ArrayList;

public class SampleList {
	
	private Plate prepPlateType;
	private Plate targetPlateType;
	
	private ArrayList<Sample> samples;
	
	//constructor..
	public SampleList(Plate prepPlateType, Plate targetPlateType) {
		samples = new ArrayList<Sample>();
		this.prepPlateType = prepPlateType;
		this.targetPlateType = targetPlateType;
	}
	
	//getters
	public ArrayList<Sample> getAllSamples() {
		return samples;
	}
	
	//modifiers
	public int addSample(Sample sample) {
		samples.add(sample);
		return samples.size();
	}
}
