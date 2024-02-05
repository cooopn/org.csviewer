package mgr;

import java.io.File;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class HelpPanelContentModel {
	private String basePath;
	private String folder;
	private List<String> pages = new ArrayList();
	
	public void addPage(String pageInHtmlTags) {
		pages.add(pageInHtmlTags);
	}
	
	public int pageCount() {
		return pages.size();
	}
	
	public String getPageByIndex(int index) {
		return pages.get(index);
	}

	public String getTestPage() {
		String htmlRoot = "html/";
		String html = "";
		String line;
		String file = "WelcomeWithRelativePath.html";
		try {
			Scanner htmlReader = new Scanner(new File(htmlRoot + file));
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
			JOptionPane.showConfirmDialog(null, "See the image?", "In img adapter", 0, 0, 
					new ImageIcon("C:/Users/itzCo/Desktop/Class Files/CSC 481.001/proj1_resources/org.csviewer/html/image/monkeyIcon.png"));
			System.out.println("Chgd==>" + newLine);
		}
		return newLine;
	}
}
