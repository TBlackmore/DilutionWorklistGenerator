
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
	
	//Constructors
	public Plate(int rows, int cols, String plateType, double wellVol, double minAspVol) {
		this.rows = rows;
		this.cols = cols;
		this.wellVol = wellVol;
		this.minAspVol = minAspVol;
		this.plateType = plateType;
		this.dils = new Dilution[rows][cols];
		
		for (int r = 0 ; r < rows ; r++) {
			for (int c = 0 ; c < cols ; c++) {
			}
		}
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
