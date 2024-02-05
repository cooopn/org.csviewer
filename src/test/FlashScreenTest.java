package test;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import driver.TestDriver;

public class FlashScreenTest {

	public static void main(String[] args) {
		ImageIcon testScreen = new ImageIcon("images/TestScreen.jpg");
		JFrame f = new JFrame("Screen Test");
		f.setUndecorated(true);
		f.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				TestDriver.main(null);
			}
		});
		f.add(new JLabel(testScreen));
		f.pack();
		f.setVisible(true);
	}

}
