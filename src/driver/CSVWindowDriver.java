package driver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import gui.BottomPanel;
//import HelpPanelWi

import gui.HelpPanelWithHtmlContents;
import gui.ImagePanel;
import mgr.HelpPanelContentBuilder;
import mgr.HelpPanelContentModel;

public class CSVWindowDriver extends JFrame {
	private final JTabbedPane pane = new JTabbedPane();
    private JPanel contentPane;
    private BottomPanel bPanel = new BottomPanel();
    private ImagePanel iPanel = new ImagePanel();
	
	public CSVWindowDriver() {
		super("CSViewer for Analysts - V1.1");
		this.setIconImage(new ImageIcon("html/image/monkeyIcon.png").getImage());
		
		JPanel leftColumn = new JPanel();
		leftColumn.setLayout(new BorderLayout());
		ImageIcon csvIconImage = new ImageIcon("images/monkeyIcon.png");
		
		JPanel welcomePanel = new HelpPanelWithHtmlContents(); /*= new JPanel();
		welcomePanel.add(new JLabel(csvIconImage));*/
		pane.addTab("Welcome", welcomePanel);
		leftColumn.add(pane);
		leftColumn.add(bPanel, BorderLayout.SOUTH);

		JPanel measurePanel = new JPanel();
		measurePanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Measure Data for Selected Animal"),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,measurePanel, iPanel);
		splitPane.setResizeWeight(0.75);
		JPanel rightColumn = new JPanel(new GridLayout(1,1));
		rightColumn.add(splitPane);
		
		add(leftColumn, BorderLayout.CENTER);
		add(rightColumn, BorderLayout.EAST);
		
		JMenu jmHelp = new JMenu("Help");
		JMenuItem jmiWelcome = new JMenuItem("Intro");
		JMenuItem jmiMeasure = new JMenuItem("Measure");
		
		//creates Bones for key
		JMenu jmHelp2 = new JMenu("Key");
		JMenuItem jmiBone1 = new JMenuItem("Ulna");
		//Jmenuitme of each bone


		HelpPanelWithHtmlContents p = new HelpPanelWithHtmlContents();

		ActionListener al = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String helpTopic = e.getActionCommand();
				p.loadPagesForTopic(helpTopic);
				p.setContentToInitialPage();
				CSVWindowDriver.this.repaint();
			}
		};

		//Implements the Drop Downs
		jmiWelcome.addActionListener(al);
		jmiMeasure.addActionListener(al);
		jmiBone1.addActionListener(al);
		jmHelp.add(jmiWelcome);
		jmHelp.add(jmiMeasure);
		jmHelp2.add(jmiBone1);

		JMenuBar mb = new JMenuBar();
		mb.add(jmHelp);
		mb.add(jmHelp2);
		this.setJMenuBar(mb);

		setVisible(true);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		startCSViewer();
	}

	public static void startCSViewer() {
		new CSVWindowDriver();
	}

}
