package gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private JLabel label = new JLabel();
	
	public ImagePanel() {
		label.setIcon(new ImageIcon("images/MeasureImage.jpg"));
		this.add(label);
	}
}
