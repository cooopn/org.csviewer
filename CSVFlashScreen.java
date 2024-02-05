import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CSVFlashScreen {

	public static void main(String[] args) {
		ImageIcon csvFlashImage = new ImageIcon("images/SplashPageImage.png");
		JFrame frameless = new JFrame("Not Showing");
		frameless.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frameless.dispose();
				CSVWindowDriver.startCSViewer();
			}
			
		});
		frameless.add(new JLabel(csvFlashImage));
		frameless.setUndecorated(true);
		frameless.setVisible(true);
		frameless.pack();
		frameless.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
