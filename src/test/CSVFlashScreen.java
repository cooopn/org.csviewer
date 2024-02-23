package test;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

//import org.apache.poi.ss.usermodel.Color;

import main.CSViewerMain;


public class CSVFlashScreen {

    public static void main(String[] args) {
        // Load the image to be displayed on the splash screen
        ImageIcon csvFlashImage = new ImageIcon("images/SplashPageImage.png");

        // Create a frameless JFrame
        JFrame frameless = new JFrame("Not Showing");
        

        // Add a mouse listener to the frame
        frameless.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // When mouse is clicked, dispose the frame and start the CSVViewer
                frameless.dispose();
                //CSVWindowDriver.startCSViewer();
                CSViewerMain.startCSViewer();
            }
        });

        // Add the image to a JLabel and add the JLabel to the frame
        frameless.add(new JLabel(csvFlashImage));

        // Make the frame undecorated (no title bar, borders, etc.)
        frameless.setUndecorated(true);

        // Set the frame to be visible
        frameless.setVisible(true);

        // Adjust the size of the frame to fit its contents
        frameless.pack();

        // Set the default close operation to dispose the frame when closed
        frameless.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
