
public class Plate {
	//Variables
	private int rows;
	private int cols;
	
	//Well vol is used as is, if an empty head volume is required a reduced wellVol should be defined.
	private double wellVol;
	private double minAspVol;
	private String plateType;
	private String plateName;
	
	
	//Constructors
	public Plate(int rows, int cols, String plateType, double wellVol, double minAspVol) {
		this.rows = rows;
		this.cols = cols;
		this.wellVol = wellVol;
		this.minAspVol = minAspVol;
		this.plateType = plateType;
		
		for (int r = 0 ; r < rows ; r++) {
			for (int c = 0 ; c < cols ; c++) {
			}
		}
	}
	
	//Methods
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
