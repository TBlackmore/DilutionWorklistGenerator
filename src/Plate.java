
public class Plate {
	//Variables
	private int rows;
	private int cols;
	private String name;
	private String labware;
	
	//Well vol is used as is, if an empty head volume is required a reduced wellVol should be defined.
	private double wellVol;
	private double minAspVol;
	private String plateType;
	private String plateName;
	private Dilution[][] dils;
	
	//Base constructors (defaults to 96 well plate)
	public Plate(String name, String plateType, double wellVol, double minAspVol) {
		this.rows = 8;
		this.cols = 12;
		this.name = name;
		this.wellVol = wellVol;
		this.minAspVol = minAspVol;
		this.plateType = plateType;
		this.dils = new Dilution[rows][cols];
		
//		for (int r = 0 ; r < rows ; r++) {
//			for (int c = 0 ; c < cols ; c++) {
//			}
//		}
	}
	//Constructor with custom rows/cols
	public Plate(int rows, int cols, String name, String plateType, double wellVol, double minAspVol) {
		this(name, plateType, wellVol, minAspVol);
		this.rows = rows;
		this.cols = cols;
		//overwrite Dilution array with new dimensions
		this.dils = new Dilution[rows][cols];
	}
	
	public int wellCol (int wellNumber) {
		return 1 + ((wellNumber - 1) % cols);
	}
	
	public int wellRow (int wellNumber) {
		return 1 + ((wellNumber - 1) / cols);
	}
	
	public void setDilution(Dilution dilution, int wellNumber) {
		this.dils[wellRow(wellNumber)][wellCol(wellNumber)] = dilution;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLabware() {
		return this.labware;
	}
	
	public int getRowSize() {
		return this.rows;
	}
	
	public int getColumnSize() {
		return this.cols;
	}

	//Return the maximum dilution that can be performed in this plate type
	public double getMaxDil() {
		return (1/(minAspVol/wellVol));
	}
	
	public double getMinAspVol() {
		return this.minAspVol;
	}
	
	public double getWellVol() {
		return this.wellVol;
	}
}
