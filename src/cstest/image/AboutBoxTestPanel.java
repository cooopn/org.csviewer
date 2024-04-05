package cstest.image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import main.CSViewerMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

public class AboutBoxTestPanel extends JPanel {
	public AboutBoxTestPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(560, 300));
		ImageIcon islandIcon = new ImageIcon("images/AboutLogo.jpg");
		JLabel jlblAboutLogo = new JLabel(islandIcon);
		jlblAboutLogo.setBounds(0, 0, 250, 300);
		this.add(jlblAboutLogo);
		
		JPanel creditBlock = prepareCreditBlock();
		creditBlock.setBounds(250, 10, 300, 200);
		this.add(creditBlock);
		JPanel fundersBlock = prepareFundersBlock();
		fundersBlock.setBounds(250, 210, 300, 85);
		this.add(fundersBlock);
	}

	private JPanel prepareCreditBlock() {
		JPanel p = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawString("CSViewer", 10, 100);
			}
		};
		p.setLayout(null);
		p.setPreferredSize(new Dimension(300, 200));
		return p;
	}

	private JPanel prepareFundersBlock() {
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setPreferredSize(new Dimension(300, 85));
		TitledBorder blockBorder = 
				BorderFactory.createTitledBorder("Funded by:");
		p.setBorder(blockBorder);
		p.setBackground(Color.WHITE);

		// add funder logos
		String[] funders = {"clas", "nsf", "cprc", "nih", "tamu"};
		ImageIcon funderIcon;
		JLabel jlblFunderLogo;
		for (int i=0; i<funders.length; i++) {
			funderIcon = new ImageIcon("images/" + funders[i] + "54x54.png");
			jlblFunderLogo = new JLabel(funderIcon);
			jlblFunderLogo.setBounds(8 + i*58, 20, 54, 54);
			p.add(jlblFunderLogo);
		}
		
		return p;
	}

	public static void main(String[] args) {
		JFrame d = new JFrame("About CS Viewer");
		d.setIconImage(CSViewerMain.CS_VIEWER_LOGO);
		d.add(new AboutBoxTestPanel(), BorderLayout.CENTER);
		JButton okButton = new JButton("OK");
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));				
		bottomPanel.add(okButton);
		d.add(bottomPanel, BorderLayout.SOUTH);
		d.pack();
		d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.setVisible(true);
	}

}
