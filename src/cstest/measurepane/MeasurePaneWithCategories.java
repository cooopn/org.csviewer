package cstest.measurepane;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.CategoryPanelWithBorder;

public class MeasurePaneWithCategories {

	public static void main(String[] args) {
		JFrame f = new JFrame("Border Test");
		CategoryPanelWithBorder p = preparePanel();

		Container c = f.getContentPane();
		c.setLayout(null);
		
		// quick demo for creating top panel with 3 categories
		JPanel topPanel = preparePanel();
		topPanel.setBounds(10, 10, 100, 250);
		c.add(topPanel);
		JLabel skullIllust = new JLabel(new ImageIcon("images/MeasureImageOld.png"));
		skullIllust.setBounds(10, 260, 100, 230);
		c.add(skullIllust);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 550); //.pack();
		f.setVisible(true);
	}

	private static CategoryPanelWithBorder preparePanel() {
		String categoryName = "Skull";
		String[] measureNames = {"Zy-Zy","Ba-Br","Eu-Eu","Gl-Op",
					"Go-Co","Go-Go","Po-Go","Al-MxF"};
		return new CategoryPanelWithBorder(categoryName,
				measureNames);
	}

}