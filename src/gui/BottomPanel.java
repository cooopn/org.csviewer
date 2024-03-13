package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import mgr.AnimalFamilyInfo;
//import mgr.GeneralBean;
import mgr.GeneralBean;

public class BottomPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private String[] dataNames = {"SubjId:", "MomId:", "DadId:", "Sex:", 
			"FounderId:", "Pedigree:", "SiblingNo:","DoB:","B-Group:",
			"Unicode:"};
	//private JLabel[] data = new JLabel[10]; 
	private JTextField[] data = new JTextField[10]; 
	private JPanel summaryPanel, introPanel;

	public BottomPanel() {
		createSumPanel();
		createIntroPanel();
		
		this.setLayout(new CardLayout());
		add(introPanel, "intro");
		add(summaryPanel, "summary");
		//summaryPanel.setVisible(false);
		//introPanel.setVisible(true);
	}
	
	private void createIntroPanel() {
		introPanel = new JPanel();
		JLabel introWording = new JLabel("Animal information will be displayed here "
				+ "when a subject is selected in the family tree.");
		TitledBorder blockBorder = 
				BorderFactory.createTitledBorder("Animal Info Summary");
		introPanel.setBorder(blockBorder);
		introPanel.add(introWording);
	}

	private void createSumPanel() {
		summaryPanel = new JPanel(new GridLayout(2,10));
		JLabel dataLabel;
		JTextField aCell;
		int i = 0;
		Font labelFont = new Font(this.getFont().getFontName(), Font.BOLD,
					this.getFont().getSize());
		for (String name : dataNames) {
			dataLabel = new JLabel(name, JLabel.LEFT);
			dataLabel.setFont(labelFont);
			dataLabel.setOpaque(true);
			dataLabel.setForeground(Color.black);
			//dataLabel.setBorder(blackline);
			// add the cell to the JPanel
			summaryPanel.add(dataLabel);
			
			aCell = new JTextField(10); //JLabel();
			aCell.setEditable(false);
			data[i] = aCell;
			aCell.setForeground(Color.GRAY);
			i++;
			summaryPanel.add(aCell);
		}
		
	}
	
	public void editData(String[] newdata) {
		for(int i = 0; i < 10; i++) {
			data[i].setText(newdata[i]);
		}
		
		((CardLayout) this.getLayout()).show(this, "summary");
	}
	
	public void editData(AnimalFamilyInfo newdata)
	{
		//Create a new GeneralBean from the AnimalFamilyInfo
		GeneralBean gBean = new GeneralBean(newdata);
		
		String[] beanData = gBean.getGeneralInfo();
		//this.summaryPanel.setVisible(true);
		
		for(int i = 0; i < beanData.length; i ++)
		{
			data[i].setText(beanData[i]);
			//System.out.print(beanData[i] + " ");
		}
		//System.out.println();
		((CardLayout) this.getLayout()).show(this, "summary");
	}
	
}