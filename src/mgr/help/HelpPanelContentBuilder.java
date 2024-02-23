package mgr.help;

import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HelpPanelContentBuilder {
	private HelpPanelContentModel modelWithPages;
	private String htmlRootPath;
	private String contentDir;
	
	/*
	public HelpPanelContentBuilder(String htmlRootPath, String contentDir) {
		this.htmlRootPath = htmlRootPath;
		this.contentDir = contentDir;
		pages = new ArrayList<HelpPanelContentModel>();
		
		
	}
	*/

	public HelpPanelContentBuilder() {
		htmlRootPath = new File("").getAbsolutePath();
	}
	
	public HelpPanelContentModel retrievePagesFromDir(String dir) {
		modelWithPages = new HelpPanelContentModel();
		String absDirPath = this.htmlRootPath + "\\" + dir;
		System.out.println("absDirPath=" + absDirPath);
		File folder = new File(absDirPath);
		
		for (File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(".html")) {
				System.out.println(fileEntry.getName());
				modelWithPages.addPage(
						getPage(dir + "\\" + fileEntry.getName()));
			}
		}

		return modelWithPages;
	}
	
	public String getPage(String fileName) {
		/*
		String htmlRoot = "html/";
		String file = "WelcomeWithRelativePath.html";
		*/
		String html = "";
		String line;
		try {
			Scanner htmlReader = new Scanner(new File(fileName));
			while (htmlReader.hasNextLine()) {
				line = htmlReader.nextLine();
				line = changeImgSrcToAbsPath(line);
				html += line + "\r\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}

	private String changeImgSrcToAbsPath(String line) {
		String newLine = line;
		if (line.contains("src=\"html/image")) {
			String root = new File("").getAbsolutePath();
			System.out.println(line);
			System.out.println(root);
			
			String htmlImageBase = "C:/Users/itzCo/Desktop/Class Files/CSC 481.001/proj1_resources/org.csviewer";
			newLine = line.replace("src=\"html/image", 
					"src=\"file:///" + htmlImageBase + "/html/image");
			/*
			JOptionPane.showConfirmDialog(null, "See the image?", "In img adapter", 0, 0, 
					new ImageIcon("C:/Users/Martin Zhao/eclipse-workspace/CSC481/html/image/monkeyIcon.png"));
			*/
			System.out.println("Chgd==>" + newLine);
		}
		return newLine;
	}

}
