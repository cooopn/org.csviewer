package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BottomPanel extends JPanel {
	private String[] dataNames = {"SubjetId:", "MomId:", "DadId:", "Gender:", 
			"FounderId:", "Pedigree:", "SiblingNo:","DoB:","B-Group:",
			"Unicode:"};
	private JLabel[] data = new JLabel[10]; 

	public BottomPanel() {
		this.setLayout(new GridLayout(2,10));
		JLabel dataLabel;
		JLabel aCell;
		int i = 0;
		Font labelFont = new Font(this.getFont().getFontName(), Font.BOLD,
					this.getFont().getSize());
		for (String name : dataNames) {
			dataLabel = new JLabel(name, JLabel.CENTER);
			dataLabel.setFont(labelFont);
			dataLabel.setOpaque(true);
			dataLabel.setForeground(Color.black);
			//dataLabel.setBorder(blackline);
			// add the cell to the JPanel
			this.add(dataLabel);
			
			aCell = new JLabel();
			//aCell.setEditable(false);
			data[i] = aCell;
			aCell.setForeground(Color.GRAY);
			i++;
			this.add(aCell);
		}
		
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Animal Info Summary"), 
				BorderFactory.createEmptyBorder(5,5,5,5)));
	}
}
