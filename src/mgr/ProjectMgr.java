package mgr;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import main.CSViewerMain;

public class ProjectMgr {
	private File projectDir;
	private CSViewerMain csvMain;
	private JFileChooser fc;

	public ProjectMgr(CSViewerMain csvMain) {
		this.csvMain = csvMain;
		projectDir = new File(System.getProperty("user.home"));
		fc = new JFileChooser();
	}
	
	public void createProjectFolder() {
    	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	fc.setApproveButtonText("Create");
    	fc.setDialogTitle("Create Project Folder");
        int approveCode = fc.showSaveDialog(csvMain);
        if (approveCode == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            System.out.println(f);
       		f.mkdir();
       		
       		projectDir = f;
       }
	}
	
	public void saveSelectedDataset() {
    	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	fc.setApproveButtonText("Save");
    	fc.setDialogTitle("Save Animal Dataset");
        int approveCode = fc.showSaveDialog(csvMain);
        if (approveCode == JFileChooser.APPROVE_OPTION) {
           File f = fc.getSelectedFile();
           System.out.println(f);
            try {
                csvMain.getSearchMgr().getSelectedAnimalSet().writeCsv(f.getAbsolutePath());
              } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
              }
        	
        }
    }
}
