package cstest.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import gui.BasicBackgroundPanel;
import gui.image.CSImagePanel;
import cstest.tohtml.HtmlContentOverBBPanel;

//import gui.ImagePanel;

public class OverlayTest {

	public static void main(String[] args) {
		JFrame f = new JFrame("With Border");
		BasicBackgroundPanel bbp = prepareBBPanel();
		CSImagePanel imp = new CSImagePanel("images");

		HtmlContentOverBBPanel htp = new HtmlContentOverBBPanel();
		HtmlContentOverBBPanel mwp = new HtmlContentOverBBPanel("Welcome", 
				"images/CSIsland.png", "html/Welcome.html",
				new Rectangle(50, 30, 800, 400));
		
		JPanel p = new JPanel();
		p.setLayout(new CardLayout());
		p.add(bbp, "intro");
		p.add(imp, "image");
		p.add(htp, "table");
		p.add(mwp, "mainw");
		f.add(p);
		
		JButton b = new JButton("Show Image");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout)p.getLayout()).show(p, "image");
			}
			
		});
		
		JPanel controlP = new JPanel();
		controlP.add(b);
		
		b = new JButton("Show Table");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout)p.getLayout()).show(p, "table");
			}
			
		});
		controlP.add(b);
		
		b = new JButton("Show Window");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout)p.getLayout()).show(p, "mainw");
			}
			
		});
		controlP.add(b);
		
		f.add(controlP, BorderLayout.SOUTH);

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	private static BasicBackgroundPanel prepareBBPanel() {
		BasicBackgroundPanel p = new BasicBackgroundPanel(
		new ImageIcon("images/MeasureImage.jpg").getImage());

		TitledBorder tb = BorderFactory.createTitledBorder("Specimen Image Display");
		tb.setTitleColor(Color.WHITE);
		p.setBorder(tb);
		
		JTextArea introWording = new JTextArea();
		String text = "Specimen image will be displayed here "
				+ "when a subject with photos is selected in "
				+ "the family tree.";
		introWording.setText(text);
		introWording.setEditable(false);
		introWording.setLineWrap(true);
		introWording.setWrapStyleWord(true);
		introWording.setBackground(new Color(223, 223, 223));
		introWording.setBounds(50, 30, 270, 50);
		p.add(introWording);

		return p;
	}

}
