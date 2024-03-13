package cstest.tohtml;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import joinery.DataFrame;
import joinery.DataFrame.JoinType;
import mgr.measure.MeasureDataOrganizer;
import mgr.search.AnimalFamilyOrganizer;
 
public class TestFileChooser {
    public static void main(final String[] args) {
        System.out.println(System.getProperty("user.home"));
        final JFrame frame = new JFrame("Test");
        frame.setSize(300, 200);
        //frame.setLocation(null);
        final JFileChooser fc = new JFileChooser();

        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        //fc.setApproveButtonText("Save it");
        
        DataFrame<Object> dfM = new MeasureDataOrganizer().getMeasureForKeys("Go-Go", "TL");
        DataFrame<Object> dfA = new AnimalFamilyOrganizer().getAnimalsByFamily("Q1012");
        DataFrame<Object> dfR = dfM.joinOn(dfA, JoinType.INNER, 0);

        final JButton projButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                fc.setApproveButtonText("Create it");
            	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int approveCode = fc.showSaveDialog(frame);
                if (approveCode == JFileChooser.APPROVE_OPTION) {
                   File f = fc.getSelectedFile();
                   System.out.println(f);
               		f.mkdir();
               }
           }
       });
        projButton.setText("Create Proj");
        frame.add(projButton,BorderLayout.NORTH);
        
        final JButton button = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.setApproveButtonText("Save it");
                int approveCode = fc.showSaveDialog(frame);
                if (approveCode == JFileChooser.APPROVE_OPTION) {
                   File f = fc.getSelectedFile();
                   System.out.println(f);
                	//String fileName = fc.getName();
                    try {
                       //String file = System.getProperty("user.home") + "\\" + fileName;
                        dfR.writeCsv(f.getAbsolutePath());
                        /*
                        String str = "Hello";
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                        writer.write(f.getCanonicalPath() + "|" + str);
                        writer.close();
                        */
                      } catch (IOException ex) {
                        System.out.println("An error occurred.");
                        ex.printStackTrace();
                      }
                	
                }
            }
        });
        button.setText("Save Dataset");
        frame.add(button,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
