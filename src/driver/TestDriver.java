package driver;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.HelpPanelWithHtmlContents;

public class TestDriver {

	public static void main(String[] args) {
		JFrame f = new JFrame("Test");
		JMenu jmHelp = new JMenu("Help");
		JMenuItem jmiWelcome = new JMenuItem("Intro");	
		JMenuItem jmiMeasure = new JMenuItem("Measure");
		HelpPanelWithHtmlContents p = new HelpPanelWithHtmlContents();
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String helpTopic = e.getActionCommand();
				p.loadPagesForTopic(helpTopic);
				p.setContentToInitialPage();
				p.repaint();
			}
			
		};
		jmiWelcome.addActionListener(al);
		jmiMeasure.addActionListener(al);
		jmHelp.add(jmiWelcome);
		jmHelp.add(jmiMeasure);
		JMenuBar mb = new JMenuBar();
		mb.add(jmHelp);
		f.setJMenuBar(mb);
		f.add(p);
		//f.add(controlPanel, BorderLayout.SOUTH);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

}
