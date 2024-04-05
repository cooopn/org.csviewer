package mgr.help;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import java.util.Scanner;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;

import gui.BasicBackgroundPanel;

public class HtmlContentOverBBPanel extends JPanel {
	private String htmlContents;
	private Rectangle bounds = new Rectangle(50, 30, 500, 300);
	private String title = "Measure Key Protocols";
	private String imgFile = "images/CSIsland.png";
	private String htmlFile = "html/htmltable.txt";
	
	public HtmlContentOverBBPanel() {
		htmlContents = loadHtml();
		BasicBackgroundPanel bbp = prepareBBPanel(htmlContents);
		add(bbp);
	}
	
	public HtmlContentOverBBPanel(String title, String imgFile, 
			String htmlFile, Rectangle bounds) {
		this.title = title;
		this.imgFile = imgFile;
		this.htmlFile = htmlFile;
		this.bounds = bounds;
		htmlContents = loadHtml();
		BasicBackgroundPanel bbp = prepareBBPanel(htmlContents);
		add(bbp);
	}

	private BasicBackgroundPanel prepareBBPanel(String text) {
		BasicBackgroundPanel p = new BasicBackgroundPanel(
				new ImageIcon(imgFile).getImage());
		//new ImageIcon("images/MeasureImage.jpg").getImage());

		if (title != null) {
			TitledBorder tb = BorderFactory.createTitledBorder(title);
			tb.setTitleColor(Color.WHITE);
			p.setBorder(tb);
		}
		
		JTextPane introWording = new JTextPane();
		introWording.setContentType("text/html");
		introWording.setText(text);
		introWording.setEditable(false);
		introWording.setBackground(new Color(223, 223, 223));
		introWording.setBounds(bounds);
		p.add(introWording);

		return p;
	}

	private String loadHtml() {
		Scanner input = null;
		try {
			input = new Scanner(new File(htmlFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String html = "";
		while (input.hasNextLine()) {
			html += "\n" + input.nextLine();
		}
		return html;
	}

	public static void main(String[] args) {
		HtmlContentOverBBPanel htp = new HtmlContentOverBBPanel("Welcome", 
				"images/CSIsland.png", "html/Welcome.html",
				new Rectangle(50, 10, 500, 300));
		System.out.println(htp.htmlContents);

	}

}
