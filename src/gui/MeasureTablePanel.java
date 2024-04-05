package gui;

import javax.swing.BorderFactory;
//import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import joinery.DataFrame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
//import java.util.Scanner;

public class MeasureTablePanel extends JPanel
{
	private JTable table;
	private JScrollPane sp;
	private JPanel introPanel;
	
	private String[] rowHeaders;
	private String[][] columnData;
	private String[] colHeaders;
	
	/**
	 * Constructs an information panel that consists of a JTable 
	 * with information about the selected entry
	 */
	public MeasureTablePanel()
	{
		//Instantiates the table with the proper values from the file
		//table = new JTable(columnData, colHeaders);
		table = prepareTestTable();
		//Add the table to a scroll panel
		sp = new JScrollPane(table);
		
		introPanel = prepareIntroPanel();
		
		this.setLayout(new CardLayout());
		add(introPanel, "intro");
		add(sp, "table");

		//add(sp);
	}
	
	private JPanel prepareIntroPanel() {
		JPanel introPanel = new BasicBackgroundPanel("images/MeasureTableBackground.png");
				/*new JPanel();
		introPanel.setLayout(null);
		*/
		JTextArea introWording = new JTextArea();
		String html = "Measure data will be displayed here "
				+ "when a subject is selected in the family tree.";
		introWording.setText(html);
		introWording.setEditable(false);
		introWording.setLineWrap(true);
		introWording.setWrapStyleWord(true);
		introWording.setBackground(new Color(223, 223, 223));
		introWording.setBounds(50, 30, 270, 50);
		TitledBorder blockBorder = 
				BorderFactory.createTitledBorder("Measure Data Table");
		blockBorder.setTitleColor(Color.BLACK);
		introPanel.setBorder(blockBorder);
		introPanel.add(introWording);
		return introPanel;
	}

	private JTable prepareTestTable() {
		//Headers for each row in the table
		rowHeaders = new String[]{"Zy-Zy", "Ba-Br", "Eu-Eu", "GI-Op","Go-Co", "Go-Go", 
				"Po-GO", "AI-MxF sut", "HL", "HPML", "HPAP", "HDML", "HDAP", "RL", 
				"RPML", "RPAP", "RDML", "RDAP", "UL", "FL", "FHL", "FHSI", "FPAP", "FPML" };
		//Data fields for each cell in the table
		/*
		Id,Mus CPRCMUS No,Tattoo,CS_Tattoo,Sex,
		Coronal suture,Sagittal suture,Lamboidal suture,
		Humeral Head,Distal Humerus,Humerus Lateral Epicondyle,Humerus Medial Epicondyle,
		Proximal Radius,Distal Radius,Proximal Ulna,
		Distal Ulna,Femoral Head,Distal Femur,Proximal Tibia,Distal Tibia,
		Zy-Zy,Ba-Br,Eu-Eu,Gl-Op,Go-Co,Go-Go,Po-Go,Al-MxF suture midline,
		HL,HPML,HPAP,HDML,HDAP,RL,RPML,RPAP,RDML,RDAP,UL,
		FL,FHL,FHSI,FPAP,FPML,FDML,FDAP,
		TL,TPML,TPAP,TDML,TDAP,IICrest,Pel_height,HDia,RDia,UDia,FDia,TDia,
		Pterion_Right,Pterion_Left,BMD_Hum,BMD_Fem,MCode,Pedigree,EAAD
		*/
		columnData = new String[24][2];
		
		for (int r=0; r<rowHeaders.length; r++) {
			columnData[r][0] = rowHeaders[r];
		}

		// Array used to form column headers, which are not displayed 
		// unless a scroll panel is used.
		colHeaders = new String[]{"Measure Key", "Value"};
		
		JTable newTable = new JTable(columnData, colHeaders);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		newTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		
		DefaultTableCellRenderer boldRenderer = new DefaultTableCellRenderer();
		boldRenderer.setFont(newTable.getFont().deriveFont(Font.BOLD));
		newTable.getColumnModel().getColumn(0).setCellRenderer(boldRenderer);
		return newTable;
	}
	
	public void showTableCard() {
		((CardLayout) this.getLayout()).show(this, "table");
	}

	public void editTable(DataFrame<Object> df) {
		if (df != null && df.length() > 0) 
			// set value cells with data
			for (int c=0; c<rowHeaders.length; c++) {
				columnData[c][1] = "" + df.get(0, 20+c);
				System.out.println(rowHeaders[c] + "-->" + columnData[c][1]);
			}
		else
			// set value cells to empty
			for (int r=0; r<rowHeaders.length; r++) {
				columnData[r][1] = "";
			}
		showTableCard();
	}
}
