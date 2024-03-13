package cstest.image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gui.image.CSImagePanel;

public class ImageSizeChecker {

	public static void main(String[] args) {
		String path = "csimg";

		JFrame f = new JFrame("CS Image Checker");
		f.setIconImage(new ImageIcon("images/monkeyicon.png").getImage());
		f.add(new CSImagePanel(path));
	    f.pack();
	    f.setVisible(true);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

