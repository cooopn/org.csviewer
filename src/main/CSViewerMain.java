package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import csdb.KinshipGraphEditor;
import csdb.core.GraphFrame;
import dftest.MainDriver;
import gui.AnimalSetSelectionPanel;
import gui.BottomPanel;
import gui.CSTreePanel;
import gui.HelpPanelWithHtmlContents;
import gui.MainContentPaneWithTabs;
import gui.MeasureSelectionPanel;
import measure.BoneMeasureMgr;
import mgr.AnimalFamilyInfo;
import mgr.familytree.FamilyTreeMgr;
import search.SearchMgr;

public class CSViewerMain extends JFrame {
	private MainContentPaneWithTabs tabbedPane;
    private JPanel contentPane;
	private BottomPanel bPanel = new BottomPanel();

	private FamilyTreeMgr fmgr = new FamilyTreeMgr();
	private SearchMgr sMgr = new SearchMgr();
	private BoneMeasureMgr bmMgr = new BoneMeasureMgr();

	private HelpPanelWithHtmlContents welcomePanel = new HelpPanelWithHtmlContents("html/Welcome"); // Panel for the "Welcome" tab
	private HelpPanelWithHtmlContents measureInformation = new HelpPanelWithHtmlContents("html/Measure"); // Panel for the "Measure" tab
	
	public static final Image CS_VIEWER_LOGO = 
			new ImageIcon("images/monkeyicon.png").getImage();
	
    public static void main(String[] args) {
	   startCSViewer();
    }
   
    public static void startCSViewer() {
       //Schedule a job for the event dispatch thread:
       //creating and showing this application's GUI.
       SwingUtilities.invokeLater(new Runnable(){
           public void run(){
               //Turn off metal's use of bold fonts
           UIManager.put("swing.boldMetal", Boolean.TRUE);
               new CSViewerMain("Cayo Santiago Database Viewer");
           }
       });
    }
    
	public CSViewerMain(String title) {
		super(title);
        this.setIconImage(CS_VIEWER_LOGO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             
        tabbedPane = new MainContentPaneWithTabs();
        initMenu();       
        initMainPanel();
        
        addSummaryPane();

        
        contentPane.setPreferredSize(new Dimension(820, 600));
        add(contentPane);   
        pack();
        setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        setVisible(true);
	}
	
	private void addSummaryPane() {
		contentPane.add(this.bPanel, BorderLayout.SOUTH);
	}

	private void initMainPanel() {
        contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
 
        //Create a scrolled text area.
        
 
        //Add the text area to the content pane.
        tabbedPane.addTab("Welcome", welcomePanel);
		tabbedPane.addTab("Measure Information", measureInformation);
        contentPane.add(tabbedPane);
	}


	private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
		// TODO Auto-generated method stub
		
        JMenu fileMenu = new JMenu("File"); 
        JMenuItem jmItem = new JMenuItem("New");
        fileMenu.add(jmItem);
        jmItem = new JMenuItem("Save");
        fileMenu.add(jmItem);
        jmItem = new JMenuItem("Exit");
        fileMenu.add(jmItem);
        menuBar.add(fileMenu);
        
        // added a Search menu 1-19-2023
        // with a select Measure item to pop up a dialog with 8 skull measures
        //      could be hosted under Measure menu?
        JMenu searchMenu = new JMenu("Search"); 
        jmItem = new JMenuItem("Select Animal");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				tabbedPane.addTab("Animal Selection", 
						new AnimalSetSelectionPanel(sMgr));
			}
			
		});
        searchMenu.add(jmItem);
        jmItem = new JMenuItem("Select Family");
        searchMenu.add(jmItem);
        jmItem = new JMenuItem("Select Measure");
		// Analytics with measure vs. EEAD xy-plot
        searchMenu.add(jmItem);
        menuBar.add(searchMenu);
        
        JMenu familyMenu = new JMenu("Family"); 
        prepareFamilyMenu(familyMenu);
		// add family menu
        //familyMenu.add(jmItem);
        menuBar.add(familyMenu);
        
        addOtherMenus(menuBar);
        
        this.setJMenuBar(menuBar);
	}
	
	private void prepareFamilyMenu(JMenu familyMenu) {
        JMenuItem jmItem = new JMenuItem("Matrilneal");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Welcome page with intro will stay: for now
				//pane.remove(0);
				JTree tree = fmgr.getMatrilTree();
				JPanel p = new CSTreePanel(tree, CSViewerMain.this);
		    	addSelectionListener(tree);
				tabbedPane.addTab("Matril", p);
				addSelectionListener(tree);
			}
        	
        });
        familyMenu.add(jmItem);
        jmItem = new JMenuItem("Patrilneal");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JTree tree = fmgr.getPatrilTree();
				JPanel p = new CSTreePanel(tree, CSViewerMain.this);
				tabbedPane.addTab("Patril", p);
				addSelectionListener(tree);
			}
        	
        });
        familyMenu.add(jmItem);
        jmItem = new JMenuItem("Show Path");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String typeAndNode = JOptionPane.showInputDialog(
						CSViewerMain.this, 
						"mORp:AnimalId", 
						"Select tree type (m/p) and node (AnimalId)", 
						JOptionPane.INFORMATION_MESSAGE);
				JTree tree;
				char treeType = typeAndNode.charAt(0);
				String animalId = typeAndNode.split(":")[1].trim();
				String tabLabel;
				//FamilyTreeMgr mgr = new FamilyTreeMgr();
				if (treeType == 'm' || treeType == 'M') {
					tree = fmgr.getMatrilTree();
					tabLabel = "Matril-" + animalId;
					//addSelectionListener(tree);
				}
				else {
					tree = fmgr.getPatrilTree();
					tabLabel = "Patril-" + animalId;
				}
				JPanel p = new CSTreePanel(tree, CSViewerMain.this);
				tabbedPane.addTab(tabLabel, p);
				addSelectionListener(tree);
				
				synchronized(p.getTreeLock()) {
					p.validate();
				}
				
				tree.setExpandsSelectedPaths(true);
				//DefaultMutableTreeNode node = mgr.getTestNode();
				String id = typeAndNode.split(":")[1].trim();
				//DefaultMutableTreeNode node = mgr.getMatrilTreeNodeById("i69G3");
				DefaultMutableTreeNode node;
				if (treeType == 'm' || treeType == 'M') 
					node = fmgr.getMatrilTreeNodeById(id);
				else
					node = fmgr.getPatrilTreeNodeById(id);
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				tree.setSelectionPath(new TreePath(model.getPathToRoot(node)));
			}
        	
        });
        familyMenu.add(jmItem);
        
        jmItem = new JMenuItem("Select Descendants");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String founderCode = JOptionPane.showInputDialog(
						CSViewerMain.this, 
						"Found Id please...", 
						"Select Genetic Tracing Group", 
						JOptionPane.INFORMATION_MESSAGE);
				
				List<String> testNodes = prepareTestNodes();
				JTree tree = fmgr.getMatrilTree(founderCode, testNodes);
				JPanel p = new CSTreePanel(tree, CSViewerMain.this);
				tabbedPane.addTab("Genetic Tracing-"+founderCode, p);
				addSelectionListener(tree);
			}

			private List<String> prepareTestNodes() {
				//String[] nodes = "299,521,509,H77,F75,L93,O24,Z70".split(",");
				String tattooNosInCsvFormat =
						JOptionPane.showInputDialog(CSViewerMain.this, 
								"Please enter tattoo numbers separated by comma:");
				if (tattooNosInCsvFormat == null)
					tattooNosInCsvFormat = "299,521,509,H77,F75,L93,O24,Z70";
				String[] nodes = tattooNosInCsvFormat.split(",");
				System.out.print(nodes.length);
				List<String> testNodes = Arrays.asList(nodes);
				return testNodes;
			}
        });
        familyMenu.add(jmItem);
        
        jmItem = new JMenuItem("Show Kinship Tree");
        jmItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String animalTattoo = JOptionPane.showInputDialog(
						CSViewerMain.this, 
						"Animal tattoo please...", 
						"Show Kinship Tree for Animal", 
						JOptionPane.INFORMATION_MESSAGE);
				if (!animalTattoo.startsWith("\""))
					animalTattoo = "\"" + animalTattoo + "\"";
				
				KinshipGraphEditor kgEd = new KinshipGraphEditor();
				kgEd.buildGraphforAnimal(animalTattoo, 3, true);
				JFrame frame = new GraphFrame(kgEd.getKinshipGraph());
		    	frame.setVisible(true);
			}
        });
        familyMenu.add(jmItem);
  	}

	private void addOtherMenus(JMenuBar menuBar) {
		// add the Measure menu 
		JMenu menu = new JMenu("Measure");
		JMenuItem jmItem = new JMenuItem("Measure Keys");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("Select Measure", new MeasureSelectionPanel(bmMgr));
			}
        	
        });
        menu.add(jmItem);
 		menuBar.add(menu);

 		// add the Analytics menu 
		menu = new JMenu("Analytics");
		jmItem = new JMenuItem("Measure vs. EAAD");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//JPanel chartPanel = new SkullMeasurePanel().getChartPanel();
				//new MainDriver().testXyPlotTab(tabbedPane);
				// now using live selection
				new MainDriver().testXyPlotTab(tabbedPane, 
						sMgr.selectAnimals(), bmMgr.selectMeasures());
			}
        	
        });
        menu.add(jmItem);
        
		// Analytics with xy-plot with measure1 as X 
		jmItem = new JMenuItem("Measure-1 as X");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// te be added
			}
        	
        });
        menu.add(jmItem);
 		menuBar.add(menu);
 		
		menu = new JMenu("Dental");
		menuBar.add(menu);
		menu = new JMenu("Pathology");
		menuBar.add(menu);
		menu = new JMenu("Help");
		
		jmItem = new JMenuItem("About CS Viewer");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog aboutBox = new JDialog(CSViewerMain.this, "About CS Viewer");
				aboutBox.setIconImage(CS_VIEWER_LOGO);
				
				//arrangeAboutBox(aboutBox);
				arrangeAboutBoxWithImage(aboutBox);
				aboutBox.pack();
				aboutBox.setVisible(true);
				aboutBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}

			private void arrangeAboutBoxWithImage(JDialog aboutBox) {
				JButton okButton = new JButton("OK");
				JPanel bottomPanel = new JPanel();
				bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));				
				bottomPanel.add(okButton);
				//okButton.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						aboutBox.setVisible(false);
						aboutBox.dispose();
					}
					
				});
				
				aboutBox.setLayout(new BorderLayout());
				//aboutBox.setLayout(new BoxLayout(aboutBox, BoxLayout.X_AXIS));
				ImageIcon flashIcon = new ImageIcon("images/AboutBoxLayout.png");
				aboutBox.add(new JLabel(flashIcon), BorderLayout.CENTER);
				aboutBox.add(bottomPanel, BorderLayout.SOUTH);
			}
        	
        });
        menu.add(jmItem);
		menuBar.add(menu);
	}

    public void addSelectionListener(final JTree tree) {
        tree.getSelectionModel().addTreeSelectionListener(
        		new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = 
                		(DefaultMutableTreeNode) 
                		tree.getLastSelectedPathComponent();
         //       String text = selectedNode.toString();
                if (selectedNode.getUserObject() instanceof AnimalFamilyInfo) {
                	AnimalFamilyInfo an = (AnimalFamilyInfo)selectedNode.getUserObject();
           //     	text = an.toString();
                	
                	//Update measure table data
                	// comment out for now
                	System.out.println(an.getAnimalId());
                	//updateMeasureData(an); 
                	// Update Measure information
               	
                	//Update general information
                	updateSummaryData(an);
                	//System.out.println(an);
                }
            }

        });
    }
    
	private void updateSummaryData(AnimalFamilyInfo an) {
		bPanel.editData(an);
		System.out.println("in update sum: " + an.getGender());
		repaint();
	}
	
	public void updateSummaryData(String testTattoo) {
		AnimalFamilyInfo an = (AnimalFamilyInfo) fmgr.getMatrilTreeNodeById(testTattoo).getUserObject();
			bPanel.editData(an);
			repaint();	
	}

}
