package mgr.help;

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
			// Use a method to dynamically get the base path of the running Java application
			String basePath = new File("").getAbsolutePath();
	
			// Replace backslashes with forward slashes for URL compatibility
			basePath = basePath.replace("\\", "/");
	
			// Construct the absolute path
			String absoluteImagePath = "file:///" + basePath + "/html/image";
	
			// Replace the src attribute with the absolute path
			newLine = line.replace("src=\"html/image", "src=\"" + absoluteImagePath);
	
			// Debug output to console
			System.out.println("Original Line: " + line);
			System.out.println("Base Path: " + basePath);
			System.out.println("Changed Line: " + newLine);
		}
		return newLine;
	}
}
