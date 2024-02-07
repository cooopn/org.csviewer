package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.io.File;

import mgr.HelpPanelContentModel;
import mgr.HelpPanelContentBuilder;

public class HelpPanelWithHtmlContents extends JPanel {
	private JPanel controlPanel;
	private JTextPane htmlPane;
	private HelpPanelContentModel theModel;
	private HelpPanelContentBuilder theBuilder = new HelpPanelContentBuilder(); 
	
	public HelpPanelWithHtmlContents() {
		Dimension d = new Dimension(500, 300);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		htmlPane = new JTextPane();
		htmlPane.setContentType("text/html");

		JScrollPane jspHtml = new JScrollPane(htmlPane);
		
		// get html from within the class
		//String html = getHtmlInClass();
		// get html from local file directory
		
		//String html = getHtmlFromImageRoot("WelcomeWithRelativePath.html");
		// testing with dummy page in the model
		//String html = theModel.getTestPage();
		
		//loading pages from 
		theModel = theBuilder.retrievePagesFromDir("html");
		System.out.println(theModel.pageCount());
		String html = theModel.getPageByIndex(0);
		
		System.out.println(html);
		htmlPane.setText(html);
		
		//f.add(jspHtml);
		
		//int numPages = findNumPages("html/Measure"); 			//This value holds the total number of button objects 
		JPanel controlPanel = new JPanel();
		final JButton[] btns = new JButton[6];
		btns[0] = new JButton("Prev");
		btns[5] = new JButton("Next");
		btns[1] = new JButton("1");
		btns[2] = new JButton("2");
		btns[3] = new JButton("3");
		btns[4] = new JButton("4");
		btns[0].setEnabled(false);
		btns[1].setEnabled(false);
		final Map<String, Integer> curBtnIndex = new TreeMap(); 
		curBtnIndex.put("CurBtn", 1);
		btns[1].setEnabled(false);
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int btnNo = curBtnIndex.get("CurBtn");
				if (arg0.getActionCommand().equals("Next")) {
					//System.out.println(btnNo);
					if (btnNo < 4) {
						btns[0].setEnabled(true);
						curBtnIndex.put("CurBtn", btnNo+1);
						btns[btnNo].setEnabled(true);
						btns[btnNo+1].setEnabled(false);
						if (btnNo == 3) { // btnNo == 3
							btns[5].setEnabled(false);
						}
						htmlPane.setText(getDummyHtml(btnNo+1));
						//System.out.println("Hanlding btn: " + btnNo);
					}
				}
				else if (arg0.getActionCommand().equals("Prev")){
					//System.out.println(btnNo);
					if (btnNo > 1) {
						btns[5].setEnabled(true);
						curBtnIndex.put("CurBtn", btnNo-1);
						btns[btnNo].setEnabled(true);
						btns[btnNo-1].setEnabled(false);
						if (btnNo == 2) { // btnNo == 3
							btns[0].setEnabled(false);
						}
						htmlPane.setText(getDummyHtml(btnNo-1));
						//System.out.println("Hanlding btn: " + btnNo);
					}
				}
				else {
					int newBtnNo = Integer.parseInt(arg0.getActionCommand());
					if (newBtnNo != btnNo) {
						curBtnIndex.put("CurBtn", newBtnNo);
						btns[btnNo].setEnabled(true);
						btns[newBtnNo].setEnabled(false);
						if (newBtnNo == 2 || newBtnNo == 3) { 
							btns[0].setEnabled(true);
							btns[5].setEnabled(true);
						} 
						else if (newBtnNo == 4) { 
							btns[0].setEnabled(true);
							btns[5].setEnabled(false);
						} 
						else if (newBtnNo == 1) { 
							btns[5].setEnabled(true);
							btns[0].setEnabled(false);
						}
						htmlPane.setText(getDummyHtml(newBtnNo));
					}
				}
				jspHtml.repaint();
			}

			private String getDummyHtml(int pageNo) {				
				String html = "<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"<body>\r\n" + 
						"<h1 style='background-color:powderblue;'>Welcome to CSViewer for Analysts v1.1!</h1>";
				html += "<h2>A placeholder for Page #" + pageNo + ".</h2>";
				html += "</body>\r\n" + 
						"</html>\r\n";

				return html;
			}
			
		};
		
		for (JButton b : btns) {
			b.addActionListener(al);
			controlPanel.add(b);
		}
		
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.SOUTH);
		this.add(jspHtml);
		/*
		p.setMinimumSize(new Dimension(890, 625));
		p.setMaximumSize(new Dimension(890, 625));
		p.setPreferredSize(new Dimension(890, 625));
		*/
	}

	// PRE : Must pass the valid folder path to the method
	//POST : Will return the integer for the number of page numbers in the file
	//	   	 Use this number for files navigation
	public static int findNumPages(String folderPath) {
		 // Create a File object for the folder
		 File folder = new File(folderPath);

		 // Get the list of files in the folder
		 File[] files = folder.listFiles();
 
		 // Check if the folder exists and is a directory
		 if (folder.exists() && folder.isDirectory()) {
			 // Check if files is not null (i.e., the folder is not empty)
			 if (files != null) {
				 // Get the number of files
				 return files.length;
			 } else {
				 // Return 0 if the folder is empty
				 return 0;
			 }
		 } else {
			 // Return -1 if the specified path is not a folder or does not exist
			 return -1;
		 }
	 }


	public void loadPagesForTopic(String helpTopic) {
		// for now
		String dir = "";
		if (helpTopic.equals("Intro"))
			dir = "html/Welcome";
		else 
			dir = "html/" + helpTopic;
			
		System.out.println();
		theModel = theBuilder.retrievePagesFromDir(dir);
		
	}

	public void setContentToInitialPage() {
		this.htmlPane.setText(theModel.getPageByIndex(0));
	}
}
