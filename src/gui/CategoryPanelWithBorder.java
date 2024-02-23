package gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CategoryPanelWithBorder extends JPanel {
	String categoryName;
	String[] measureNames;
	JPanel contentPane;
	
	private MeasureSelectionPanel msPanel;
	
	public CategoryPanelWithBorder(MeasureSelectionPanel msPanel, 
			int categoryIndex) {
		this.msPanel = msPanel;
		categoryName = msPanel.categories[categoryIndex];
		measureNames = msPanel.measureNames[categoryIndex];

		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		msPanel.chkboxInCategory[categoryIndex] = new JCheckBox[measureNames.length];
		for (int i=0; i< measureNames.length; i++) {
			msPanel.chkboxInCategory[categoryIndex][i] = new JCheckBox(measureNames[i]);
			contentPane.add(msPanel.chkboxInCategory[categoryIndex][i]);
		}
		
		this.setBorder(BorderFactory.createTitledBorder(categoryName));
		this.add(contentPane);
	}
	
	public CategoryPanelWithBorder(String categoryName,
			String[] measureNames) {
		this.categoryName = categoryName;
		this.measureNames = measureNames;
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		JCheckBox[] forNow = new JCheckBox[measureNames.length];
		for (int i=0; i< measureNames.length; i++) {
			forNow[i] = new JCheckBox(measureNames[i]);
			contentPane.add(forNow[i]);
		}
		
		this.setBorder(BorderFactory.createTitledBorder(categoryName));
		this.add(contentPane);
	}
	
	public static CategoryPanelWithBorder preparePanel() {
		String categoryName = "Skull";
		String[] measureNames = {"Zy-Zy","Ba-Br","Eu-Eu","Gl-Op",
					"Go-Co","Go-Go","Po-Go","Al-MxF"};
		return new CategoryPanelWithBorder(categoryName,
				measureNames);
	}
}