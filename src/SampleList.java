import java.util.ArrayList;

public class SampleList {
	
	private Plate prepPlateType;
	private Plate targetPlateType;
	private RunController runController;
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
		sample.setList(this);
		samples.add(sample);
		return samples.size();
	}
	
	public boolean setRunController(RunController runController) {
		this.runController = runController;
		return true;
	}
}
