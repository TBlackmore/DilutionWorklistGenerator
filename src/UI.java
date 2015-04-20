import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JScrollBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;


public class UI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UI frame = new UI();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI(RunController rc, IO io) {
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
		ArrayList<JTable> sourcePlateTables = this.platesToTables(rc.getSourcePlates());
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel sourcePlatesTab = new JPanel();
		tabbedPane.addTab("Source Plates", null, sourcePlatesTab, null);
		sourcePlatesTab.setLayout(new BorderLayout(0, 0));
		
		JScrollBar scrollBar = new JScrollBar();
		sourcePlatesTab.add(scrollBar, BorderLayout.EAST);
		
		JPanel sourcePlatesPanel = new JPanel();
		sourcePlatesTab.add(sourcePlatesPanel);
		this.addTablesToPanel(sourcePlateTables, sourcePlatesPanel);
		
		JPanel dilPlatesTab = new JPanel();
		tabbedPane.addTab("Dilution Plates", null, dilPlatesTab, null);
		dilPlatesTab.setLayout(new BorderLayout(0, 0));
		
		JScrollBar scrollBar_1 = new JScrollBar();
		dilPlatesTab.add(scrollBar_1, BorderLayout.EAST);
		
		JPanel dilPlatesPanel = new JPanel();
		dilPlatesTab.add(dilPlatesPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(sampleTableData, sampleTableHeaders);
		table.setFillsViewportHeight(true);
		//SampleListScrollPane.add(table);
		
		JScrollPane SampleListScrollPane = new JScrollPane(table);
		panel.add(SampleListScrollPane);
		
		
		System.out.println("UI Created");
		
		//table_1 = new JTable();
		//panel_1.add(table_1);
		
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
