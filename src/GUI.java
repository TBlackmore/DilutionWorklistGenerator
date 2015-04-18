import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class GUI extends JFrame {
	public GUI(RunController rc, IO io) {
		
		JPanel display = new JPanel();
		display.setSize(1200,800);
		JScrollPane scroll = new JScrollPane(display);
		
		//Import all the samples and convert their details to strings
		String[] sampleTableHeaders = io.getHeaders();
		Sample[] sampleArray = new Sample[io.getSampleList().size()];
		sampleArray = io.getSampleList().toArray(sampleArray);
		//Add the strings to a two-dimensional string array
		String[][] sampleTableData = new String[sampleArray.length][13];
		for (int i = 0; i < sampleArray.length; i++) {
			String[] sampleStrings = sampleArray[i].getSampleAsStrings();
			for (int j = 0; j < 13; j++) {
				sampleTableData[i][j] = sampleStrings[j];
			}
		}
		//Display the data in a JTable
		JTable sampleTable = new JTable(sampleTableData, sampleTableHeaders);
		sampleTable.setFillsViewportHeight(true);
		sampleTable.setAutoResizeMode(sampleTable.AUTO_RESIZE_OFF);
		JScrollPane sampleTableScrollPane = new JScrollPane(sampleTable);
		//sampleTableScrollPane.setSize(200,200);
		sampleTableScrollPane.setPreferredSize(new Dimension(150,150));
		display.add(sampleTableScrollPane);
		
		
		//Display all the source plates
		JLabel sourcePlateLabel = new JLabel("Source Plates");
		display.add(sourcePlateLabel);
		//JPanel sourcePlateTablePanel = new JPanel();		
		ArrayList<JTable> sourcePlateTables = this.platesToTables(rc.getSourcePlates());
		this.addTablesToPanel(sourcePlateTables, display);
		
		//Display all the dilution plated
		JLabel dilutionPlateLabel = new JLabel("Dilution Plates");
		display.add(dilutionPlateLabel);
		ArrayList<JTable> dilutionPlateTables = this.platesToTables(rc.getPrepPlates());
		this.addTablesToPanel(dilutionPlateTables, display);
		
		
		//display.add(sourcePlateTablePanel);
		//display.setVisible(true);
		this.setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		setSize(1200,800);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		System.out.println("GUI created");
	}
	public ArrayList<JTable> platesToTables (ArrayList<Plate> plates) {
		ArrayList<JTable> tables = new ArrayList<JTable>();
		Plate p;
		for (int i = 0; i < plates.size(); i++) {
			p = plates.get(i);
			String[][] data;
			data = new String[p.getRows()][p.getCols()];
			for (int j = 0; j < p.getRows(); j++) {
				for (int k = 0; k < p.getCols(); k++) {
					Dilution d = p.getDilution(j,k);
					if (d != null) {
						data[j][k] = String.valueOf(d.getSample().getiDnum()) + ": \n"
								+ d.getSample().getName();
					}
				}
			}
			String[] tableHeaders = new String[p.getCols()];
			for (int l = 1; l <= p.getCols(); l++) {
				tableHeaders[l - 1] = String.valueOf(l);
			}
			JTable t = new JTable(data, tableHeaders);
			t.setGridColor(getBackground());
			//t.setRowHeight(50);
			tables.add(t);
		}
		return tables;
	}
	
	public void addTablesToPanel (ArrayList<JTable> tables, JPanel panel) {
		for (int i = 0; i < tables.size(); i++) {
			panel.add(tables.get(i));
		}
	}
}
