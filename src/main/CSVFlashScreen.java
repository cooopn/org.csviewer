package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CSVFlashScreen {

    public static void main(String[] args) {
        // Load the image to be displayed on the splash screen
        ImageIcon csvFlashImage = new ImageIcon("images/SplashPageImageV11.png");

        // Create a frameless JFrame
        JFrame frameless = new JFrame("Not Showing");
        
        // close the flashscreen in 3 seconds and open the main window
        Timer timer = new Timer(10000, new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
            	startMainWindow(frameless);
            }
        });
        timer.setRepeats(false);
        timer.start();

        // Add a mouse listener to the frame
        frameless.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // When mouse is clicked, dispose the frame and start the CSVViewer
            	timer.stop();
            	startMainWindow(frameless);
            }
        });
        
        // Add the image to a JLabel and add the JLabel to the frame
        frameless.add(new JLabel(csvFlashImage));

        // Make the frame undecorated (no title bar, borders, etc.)
        frameless.setUndecorated(true);

        // Set the frame to be visible & show it in the middle of screen
        frameless.setVisible(true);
        frameless.setLocationRelativeTo(null);

        // Adjust the size of the frame to fit its contents
        frameless.pack();

        // Set the default close operation to dispose the frame when closed
        frameless.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private static void startMainWindow(JFrame frameless) {
   	
        frameless.dispose();
        //CSVWindowDriver.startCSViewer();
        CSViewerMain.startCSViewer();
    }
}