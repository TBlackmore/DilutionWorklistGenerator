
public class Plate {
	//Variables
	private int rows;
	private int cols;
	private Well[][] wells;
	private String plateType;
	private String plateName;
	
	
	//Constructors
	public Plate(int rows, int cols, String plateType) {
		this.rows = rows;
		this.cols = cols;
		this.plateType = plateType;
		this.wells = new Well[rows][cols];
	}
	
	//Methods
	public int getRowSize() {
		return this.rows;
	}
	
	public int getColumnSize() {
		return this.cols;
	}
	
	public Well getWell(int row, int col) {
		return this.wells[row][col];
	}
	
	public Well[][] getWellArray() {
		return this.wells;
	}
	
}
