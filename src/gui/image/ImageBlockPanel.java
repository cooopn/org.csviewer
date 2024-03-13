package gui.image;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import gui.BasicBackgroundPanel;

public class ImageBlockPanel extends JPanel {
	private CSImagePanel imgPanel;
	private BasicBackgroundPanel bbPanel;

	public ImageBlockPanel() {
		createImagePanel();
		createIntroPanel();
		
		this.setLayout(new CardLayout());
		add(bbPanel, "intro");
		add(imgPanel, "image");		
	}

	private void createImagePanel() {
		// create a test image panel with photos in csimg folder
		imgPanel = new CSImagePanel("csvimg");
	}

	private void createIntroPanel() {
		bbPanel = new BasicBackgroundPanel(
				new ImageIcon("images/MeasureImage.jpg").getImage());
		
		// create border with a title
		TitledBorder tb = BorderFactory.createTitledBorder("Specimen Image Display");
		tb.setTitleColor(Color.WHITE);
		bbPanel.setBorder(tb);
		
		// may try TextPane with HTML later
		JTextArea introWording = new JTextArea();
		String text = "Specimen image will be displayed here "
				+ "when a subject with photos is selected in "
				+ "the family tree.";
		introWording.setText(text);
		introWording.setEditable(false);
		introWording.setLineWrap(true);
		introWording.setWrapStyleWord(true);
		introWording.setBackground(new Color(223, 223, 223));
		introWording.setBounds(50, 30, 270, 50);
		bbPanel.add(introWording);
	}
	
	public void testImagePanel() {
		((CardLayout) this.getLayout()).show(this, "image");
	}
}
