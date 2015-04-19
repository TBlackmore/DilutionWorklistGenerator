
public class Plate {
	//Variables
	private int rows;
	private int cols;
	private String name;
	private String labware;
	
	//Well vol is used as is, if an empty head volume is required a reduced wellVol should be defined.
	private double wellVol;
	private double minAspVol;
	//private String plateType;
	//private String plateName;
	private Dilution[][] dils;
	
	//Base constructors (defaults to 96 well plate)
	public Plate(String name, String plateType, double wellVol, double minAspVol) {
		this.rows = 8;
		this.cols = 12;
		this.name = name;
		this.wellVol = wellVol;
		this.minAspVol = minAspVol;
		this.labware = plateType;
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
	
	//Concstructor to copy a plate with a new name
	public Plate(Plate temPlate, String name) {
		this(temPlate.getRows(), temPlate.getCols(), name, temPlate.getLabware(), temPlate.getWellVol(), temPlate.getMinAspVol());
	}
	
	
	public int wellCol (int wellNumber) {
		System.out.println("wellCol(" + wellNumber + ") = " + (wellNumber - 1) / rows);
		return (wellNumber - 1) / rows;
		//return ((wellNumber - 1) / cols);
	}
	
	public int wellRow (int wellNumber) {
		System.out.println("wellRow(" + wellNumber + ") = " + ((wellNumber - 1) % rows));
		return ((wellNumber - 1) % rows);
	}
	
	public int totalWells () {
		return rows * cols;
	}
	
	public boolean isLastWell(int[] rowCol) {
		if ((rowCol[0] == rows) && (rowCol[1] == cols)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isLastCol(int[] rowCol) {
		if (rowCol[1] == cols) {
			return true;
		} else {
			return false;
		}
	}
	public int[] nextRowColDown (int[] rowCol) {
		int row = rowCol[0];
		int col = rowCol[1];
		if ((row == rows) && (col == cols)) {
			row = 1;
			col = 1;
		} else if (row == rows) {
			row = 1;
			col++;
		} else if (row < rows) {
			row ++;
		}  
		int[] returnRowCol = {row, col};
		return returnRowCol;
	}
	
	public int[] nextRowColRight (int[] rowCol) {
		int row = rowCol[0];
		int col = rowCol[1];
		if (col == cols) {
			col = 1;
		} else if (col < cols) {
			col++;
		}  
		int[] returnRowCol = {row, col};
		return returnRowCol;
	}
	
	public Dilution getDilution(int wellNumber) {
		System.out.println("getDilution " + wellNumber + " = " + wellRow(wellNumber) + "," + wellCol(wellNumber));
		return this.dils[wellRow(wellNumber)][wellCol(wellNumber)];
	}
	public Dilution getDilution(int rowNum, int colNum) {
		return this.dils[rowNum][colNum];
	}
	public Dilution getDilution(int[] rowCol) {
		return this.dils[rowCol[0] - 1][rowCol[1] - 1];
	}
	public int rowColToWellNumber(int[] rowCol) {
		return (rows * (rowCol[1] - 1)) + rowCol[0];
	}
	public void setDilution(Dilution d, int[] rowCol) {
		this.setDilution(d, this.rowColToWellNumber(rowCol));
	}
	
	public boolean setDilution(Dilution dilution, int wellNumber) {
		System.out.println("setDilution " + wellNumber + " = " + wellRow(wellNumber) + "," + wellCol(wellNumber));
		int setRow = wellRow(wellNumber);
		int setCol = wellCol(wellNumber);
		if (this.dils[setRow][setCol] == null) {
			this.dils[wellRow(wellNumber)][wellCol(wellNumber)] = dilution;
			dilution.setPlate(this);
			dilution.setWellNum(wellNumber);
			return true;
		} else {
			System.out.println("Error: dilution previously assigned too row " + setRow + ", column " + setCol);
			return false;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLabware() {
		return this.labware;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
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
