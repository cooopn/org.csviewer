package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
//import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import measure.BoneMeasureMgr;

public class MeasureSelectionPanel extends JPanel {
	String[] categories = {"Skull", "Upper Limb", "Lower Limb", "Joints", "Misc"};
	String[][] measureNames = {{"Zy-Zy","Ba-Br","Eu-Eu","Gl-Op",
			"Go-Co","Go-Go","Po-Go","Al-MxF"},
			{"HL",	"HPML",	"HPAP",	"HDML",	"HDAP",	"RL",	"RPML",	"RPAP",	
				"RDML",	"RDAP",	"UL",	"FL",	"FHL",	"FHSI",	"FPAP"},
			{"FPML",	"FDML",	"FDAP",	"TL",	"TPML",	"TPAP",	"TDML",	"TDAP",	"IICrest",	
					"Pel_height",	"HDia",	"RDia",	"UDia",	"FDia",	"TDia"},
			{"Humeral Head",	"Distal Humerus",	"Humerus Lateral Epicondyle",	
						"Humerus Medial Epicondyle",	"Proximal Radius",	"Distal Radius",	
						"Proximal Ulna",	"Distal Ulna",	"Femoral Head",	"Distal Femur",	
						"Proximal Tibia",	"Distal Tibia"},
			{"Pterion_Right", "Pterion_Left", "BMD_Hum", "BMD_Fem"}
		};
	JCheckBox[][] chkboxInCategory = new JCheckBox[categories.length][];

	private BoneMeasureMgr bmMgr;

	public MeasureSelectionPanel(BoneMeasureMgr bmMgr) {
		this.bmMgr = bmMgr;
		/*
		 * original layout, image needs to be in bkgrd
		this.setLayout(null);
		
		JLabel skullIllust = new JLabel(new ImageIcon("images/MeasureImageOld.png"));
		skullIllust.setBounds(10, 250, 400, 300);
		this.add(skullIllust);
		*/
		BasicBackgroundPanel bbp = new BasicBackgroundPanel(
				new ImageIcon("images/MeasureSelectBkgrd.png").getImage());
		JPanel p;
		for (int i=0; i<categories.length; i++) {
			p = new CategoryPanelWithBorder(this, i);
			p.setBounds(10 + i*170, 10, 150 + 10*i, 27*measureNames[i].length + 
					((i==0)?10:0));
			if (i == 3) {
				p.setBounds(10 + 3*170 + 20, 10, 250, 27*measureNames[i].length);
			} 
			else if (i == 4) {
				p.setBounds(10 + 3*170 + 20, 350, 250, 32*measureNames[i].length);
			}
			///this.add(p);
			bbp.add(p);
		}
		
		JButton selectBtn = new JButton("Select Measures");
		selectBtn.setBounds(20, 300, 130, 25);
		selectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Measure selected...");
				
				ArrayList<String> choices = new ArrayList<>();
				for (int catIdx = 0; catIdx < measureNames.length; catIdx++) {
					for (int keyInCat = 0; 
							keyInCat < measureNames[catIdx].length; 
							keyInCat++) {
						if (chkboxInCategory[catIdx][keyInCat].isSelected())
							choices.add(measureNames[catIdx][keyInCat]);
					}
				}
				
				System.out.println("Keys selected..." + choices);
				bmMgr.setSelectedKeys(choices.toArray(new String[choices.size()]));
			}
			
		});
		bbp.add(selectBtn);
		this.add(bbp);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Border Test");
		f.add(new MeasureSelectionPanel(null));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(820, 550); //.pack();
		f.setVisible(true);
	}
}
