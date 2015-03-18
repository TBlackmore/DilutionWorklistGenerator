import java.util.ArrayList;
import java.lang.Math;
/**
 * @author Tim
 *
 */
public class Sample {
	private String name;
	private double targetDilutionFactor;
	private Dilution source;
	private ArrayList<Dilution> prepDilutions;
	private Dilution targetDilution;
	private Dilution[] serialDilutions;
	
	private int iDnum;	
    public String sourceName;
    public String sourceLabware;
    public String sampleType;
    public int sourceWellNumber;
    public int numberOfSerialDilutions;
	public double serialDilutionFactor;
    public int dilutionReplicates;
    public int assayReplicates;
    public boolean includeInAssay;
	
	private double currentDilution = 1;
	
	public Sample (String name, double targetDilutionFactor) {
		this.name = name;
		this.targetDilutionFactor = targetDilutionFactor;
		//this.sampleList = sampleList;
		this.prepDilutions = new ArrayList<Dilution>();
		//System.out.println(currentSource);
		this.source = new Dilution(1.0);
	}
	
	/**
	 * @param name
	 * @param targetDilutionFactor
	 * @param iDnum
	 * @param sourceName
	 * @param sourceLabware
	 * @param sampleType
	 * @param sourceWellNumber
	 * @param numberOfSerialDilutions
	 * @param serialDilutionFactor
	 * @param dilutionReplicates
	 * @param assayReplicates
	 * @param includeInAssay
	 */
	public Sample(int iDnum, String name,
			String sourceName, String sourceLabware, String sampleType,
			int sourceWellNumber, double targetDilutionFactor,
			int numberOfSerialDilutions,
			double serialDilutionFactor, int dilutionReplicates,
			int assayReplicates, boolean includeInAssay) {
		this.iDnum = iDnum;
		this.name = name;
		this.sourceName = sourceName;
		this.sourceLabware = sourceLabware;
		this.sampleType = sampleType;
		this.sourceWellNumber = sourceWellNumber;
		this.targetDilutionFactor = targetDilutionFactor;
		this.numberOfSerialDilutions = numberOfSerialDilutions;
		this.serialDilutionFactor = serialDilutionFactor;
		this.dilutionReplicates = dilutionReplicates;
		this.assayReplicates = assayReplicates;
		this.includeInAssay = includeInAssay;
		
		this.prepDilutions = new ArrayList<Dilution>();
		this.source = new Dilution(1.0);
	}
	
	public ArrayList<Dilution> getPrepDilutions() {
		return prepDilutions;
	}

	public void setPrepDilutions(ArrayList<Dilution> prepDilutions) {
		this.prepDilutions = prepDilutions;
	}

	public Dilution getTargetDilution() {
		return targetDilution;
	}

	public void setTargetDilution(Dilution targetDilution) {
		this.targetDilution = targetDilution;
	}

	public Dilution[] getSerialDilutions() {
		return serialDilutions;
	}

	public void setSerialDilutions(Dilution[] serialDilutions) {
		this.serialDilutions = serialDilutions;
	}

	public String getName() {
		return name;
	}

	public double getTargetDilutionFactor() {
		return targetDilutionFactor;
	}

	public Dilution getSource() {
		return source;
	}

	public int getiDnum() {
		return iDnum;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getSourceLabware() {
		return sourceLabware;
	}

	public String getSampleType() {
		return sampleType;
	}

	public int getSourceWellNumber() {
		return sourceWellNumber;
	}

	public int getNumberOfSerialDilutions() {
		return numberOfSerialDilutions;
	}

	public double getSerialDilutionFactor() {
		return serialDilutionFactor;
	}

	public int getDilutionReplicates() {
		return dilutionReplicates;
	}

	public int getAssayReplicates() {
		return assayReplicates;
	}

	public boolean isIncludeInAssay() {
		return includeInAssay;
	}

	public double getCurrentDilution() {
		return currentDilution;
	}

	//	public boolean setTargetDilution(Dilution targetDilution) {
//		this.targetDilution = targetDilution;
//		return true;
//	}
//	public double getTargetDilutionFactor() {
//		return this.targetDilutionFactor;
//	}
//	
//	public ArrayList<Dilution> getPrepDilutions() {
//		return this.prepDilutions;
//	}
//	public Dilution getTargetDilution() {
//		return this.targetDilution;
//	}
//	public String getName() {
//		return this.name;
//	}
	public String getInfo() {
		String info;
		info = "Sample Name: " + this.name + "\n";
		for (int i = 0 ; i < prepDilutions.size() ; i++) {
			info = info + "S: " + prepDilutions.get(i).getDetails() + "\n";
		}
		info = info + "T: " + targetDilution.getDetails();
		return info;
	}
}
