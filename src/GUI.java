import javax.swing.*;

import java.awt.Dimension;
import java.util.ArrayList;

public class GUI extends JFrame {
	public GUI(RunController rc, IO io) {
		
		JPanel display = new JPanel();
		
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
		sampleTableScrollPane.setPreferredSize(new Dimension(250,250));
		display.add(sampleTableScrollPane);
		
		//Display the source plate
		Plate p = rc.getSourcePlates().get(0);
		String[][] data;
		data = new String[p.getRows()][p.getCols()];
	    for (int i = 0; i < p.getRows(); i++) {
	    	for (int j = 0; j < p.getCols(); j++) {
	    		Dilution d = p.getDilution(i,j);
	    		if (d != null) {
	    			data[i][j] = d.getSample().getName();
	    		}
	    	}
	    }
	    String[] plateHeaders = {"1","1","1","1","1","1","1","1","1","1","1","1"};
	    JTable pTable = new JTable(data, plateHeaders);
		display.add(pTable);
		//display.setVisible(true);
		add(display);
		setSize(1600,800);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		System.out.println("GUI created");
	}
}
