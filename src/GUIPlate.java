import javax.swing.*;

import java.awt.Dimension;
import java.util.ArrayList;

public class GUIPlate extends JFrame{
	private Plate p;
	private JTable pTable = new JTable();
	private String[][] data;
	
	public GUIPlate (Plate p) {
		this.p = p;
		data = new String[p.getRows()][p.getCols()];
	}
	public void showNames() {
		for (int i = 0; i < p.getRows(); i++) {
			for (int j = 0; j < p.getCols(); j++) {
				data[i][j] += p.getDilution(i,j).getSample().getName();
			}
		}
	}
}
