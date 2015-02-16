
public class Well {
	private Plate myPlate;
	private Dilution dilution;
	private int row;
	private int col;
	//private int pos;
	
	public Well (Plate myPlate) {
		this.myPlate = myPlate;
	}
	
	public Well (Plate myPlate, int row, int col) {
		this.myPlate = myPlate;
		this.row = row;
		this.col = col;
	}
	
	public Plate getPlate() {
		return this.myPlate;
	}
	
	public boolean setDilution(Dilution dilution) {
		if (this.dilution != null) {
			return false;
		} else {
			this.dilution = dilution;
			return true;
		}
	}
}
