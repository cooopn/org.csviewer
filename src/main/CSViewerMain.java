package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
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
import gui.BasicBackgroundPanel;
import gui.BottomPanel;
import gui.CSTreePanel;
//import gui.HelpPanelWithHtmlContents;
import gui.MainContentPaneWithTabs;
import gui.MeasureTablePanel;
import gui.image.CSImageEntity;
import gui.image.ImageBlockPanel;
import gui.measure.MeasureSelectionPanel;
import gui.search.AnimalSetSelectionPanel;
import mgr.AnalyticsMgr;
import mgr.AnimalFamilyInfo;
import mgr.ProjectMgr;
import mgr.familytree.FamilyTreeMgr;
import mgr.help.HtmlContentOverBBPanel;
import mgr.measure.BoneMeasureMgr;
import mgr.search.SearchMgr;

public class CSViewerMain extends JFrame {
	private MainContentPaneWithTabs tabbedPane;
    private JPanel contentPane;
	private BottomPanel bPanel = new BottomPanel();
	
	//private ImagePanel iPanel = new ImagePanel();
	//private CSImagePanel iPanel = new CSImagePanel("csimg");
	private MeasureTablePanel mPanel = new MeasureTablePanel();


	private FamilyTreeMgr fmgr = new FamilyTreeMgr();
	private SearchMgr sMgr = new SearchMgr();
	private BoneMeasureMgr bmMgr = new BoneMeasureMgr();
	private AnalyticsMgr aMgr = new AnalyticsMgr(sMgr, bmMgr);
	private ProjectMgr pMgr;

	/*
	// Panel for the "Welcome" tab
	private HelpPanelWithHtmlContents welcomePanel = 
			new HelpPanelWithHtmlContents("html/Welcome"); 
			*/
	protected ImageBlockPanel imageBlockPanel;
	//private HelpPanelWithHtmlContents measureInformation = 
	//		new HelpPanelWithHtmlContents("html/Measure"); // Panel for the "Measure" tab
	
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

        addRightColumn();
        
        
        contentPane.setPreferredSize(new Dimension(820, 600));
        add(contentPane);   
        pack();
        setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        setVisible(true);
	}
	
	private void addRightColumn() {
		JPanel rightPanel = new JPanel();
		Dimension fixedSize = new Dimension(380, 600);
		rightPanel.setMaximumSize(fixedSize);
		rightPanel.setPreferredSize(fixedSize);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(mPanel);
		imageBlockPanel = new ImageBlockPanel();
		rightPanel.add(imageBlockPanel);
		//rightPanel.add(iPanel);
		getContentPane().add(rightPanel, BorderLayout.EAST);
	}

	private void addSummaryPane() {
		/*
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5,5,5,5));
		p.add(bPanel);
		*/
		contentPane.add(bPanel, BorderLayout.SOUTH);
	}

	private void initMainPanel() {
        contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
 
        JPanel output = new HtmlContentOverBBPanel(null, 
				"images/CSIslandWithNaviBar.png", "html/Welcome.html",
				new Rectangle(50, 60, 700, 300));
        
        /*new JPanel();
        output.setLayout(new BorderLayout());
        JButton welcomeLabel = new JButton("Welcome to CSViewer for Analyst v1.1!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 38));
        Dimension d = new Dimension(100, 500);
        output.setPreferredSize(d);
        output.setMinimumSize(d);
        welcomeLabel.setBackground(Color.GREEN);
        output.add(welcomeLabel, BorderLayout.NORTH);
        output.add(new JLabel(new ImageIcon("images/CSIsland.png")));
        //output.add(new JLabel(new ImageIcon("images/CSRhesusMM.jpg")));
         */
        JScrollPane scrollPane = new JScrollPane(output);
 
        //Add the text area to the content pane.
        tabbedPane.addTab("Welcome", scrollPane);       
        
        /*
        tabbedPane.addTab("Welcome", welcomePanel);
        */
		//tabbedPane.addTab("Measure Information", measureInformation);

        contentPane.add(tabbedPane);
	}


	private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
		// TODO Auto-generated method stub
		
        JMenu fileMenu = new JMenu("File"); 
        
        // for creating a new project folder
        JMenuItem jmItem = new JMenuItem("New");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pMgr == null)
					pMgr = new ProjectMgr(CSViewerMain.this);
				pMgr.createProjectFolder();
			}
        	
        });
        fileMenu.add(jmItem);
        
        // for saving a data set file to the project folder
        jmItem = new JMenuItem("Save");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pMgr == null)
					pMgr = new ProjectMgr(CSViewerMain.this);
				pMgr.saveSelectedDataset();
			}
        	
        });
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
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
			
		});
        searchMenu.add(jmItem);
        
        // not doing anything yet 
        jmItem = new JMenuItem("Select Catalog");
        searchMenu.add(jmItem);
        
        // test MeasureTablePanel for now Mar-6-24
        jmItem = new JMenuItem("Select Measure");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				mPanel.showTableCard();
			}
			
		});
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
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
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
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
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
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
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
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
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
        
        jmItem = new JMenuItem("Family Interactions");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/FamilyInteractions.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Family Interactions", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        familyMenu.add(jmItem);
  	}

	private void addOtherMenus(JMenuBar menuBar) {
		// add the Measure menu 
		JMenu menu = new JMenu("Measure");
		JMenuItem jmItem = new JMenuItem("Select Measure");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("Select Measure", new MeasureSelectionPanel(bmMgr));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);
        jmItem = new JMenuItem("Measure Keys");
        jmItem.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/Measure Keys.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Measure Keys", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        });
        menu.add(jmItem);
        menuBar.add(menu);
        
		// add the Image menu 
		menu = new JMenu("Image");
        jmItem = new JMenuItem("Select Image");
        jmItem.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent evt) {
				imageBlockPanel.testImagePanel();
	        }
		});
		menu.add(jmItem);
		menuBar.add(menu);


		// add the Group menu 
		menu = new JMenu("Group");
		jmItem = new JMenuItem("Historical Groups");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/HistoricalCSSocialGroups.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Historical Groups", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);

		jmItem = new JMenuItem("Birth Count By Group");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/SocialGroupHeadCount.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Birth Count By Group", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);
        
        jmItem = new JMenuItem("Birth Group By Family");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/BirthGroupByFamily.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Birth Group By Family", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);

        jmItem = new JMenuItem("Group Interaction");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/GroupInteractions.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Group Interaction", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);

        jmItem = new JMenuItem("Sample Migration");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/MaleMigration-xxx.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Migration-99L", scrollPane);
		        tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);
		menuBar.add(menu);
		
		// add the Catalog menu 
		menu = new JMenu("Catalog");
		jmItem = new JMenuItem("Entries By Founder");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/EntriesByFounder.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
		        tabbedPane.addTab("Entries By Founder", scrollPane);
		        tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
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
				//new AnalyticsMgr(sMgr, bmMgr)
				aMgr.setXyPlotMode(AnalyticsMgr.EAAD);
				aMgr.buildXyPlotTab(tabbedPane); //testXyPlotTab(tabbedPane);
				//MainDriver(bmMgr).testXyPlotTab(tabbedPane, 
				//		sMgr.selectAnimals(), bmMgr.selectMeasures());
			}
        	
        });
        menu.add(jmItem);
        
		// Analytics with xy-plot with measure1 as X 
		jmItem = new JMenuItem("Measure-1 as X");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				aMgr.setXyPlotMode(AnalyticsMgr.X_IS_1);
				aMgr.buildXyPlotTab(tabbedPane); //testXyPlotTab(tabbedPane);
			}
        	
        });
        menu.add(jmItem);
 		menuBar.add(menu);
        
		// Analytics with column chart for categorical measures 
		jmItem = new JMenuItem("Column Chart");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				aMgr.buildColumnChartTab(tabbedPane); 
			}
        	
        });
        menu.add(jmItem);
 		menuBar.add(menu);
 		
 		// add head count trend chart
		jmItem = new JMenuItem("Head Count Trend");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel chartPanel = new JPanel();
				chartPanel.add(new JLabel(
						new ImageIcon("images/CSHeadCountBreakdown.png")));
		        JScrollPane scrollPane = new JScrollPane(chartPanel);
				tabbedPane.addTab("CS Head Count By Year", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);

 		// add Dental menu with a default photo
		menu = new JMenu("Dental");
		jmItem = new JMenuItem("Show Photos");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Image img = new ImageIcon("images/#3316 Tooth.jpg").getImage();
				BasicBackgroundPanel bbp = new BasicBackgroundPanel(
						CSImageEntity.getImageToFitKeepRatio(img, 800,  600));
		        JScrollPane scrollPane = new JScrollPane(bbp);
				tabbedPane.addTab("Dental Defect", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);
		menuBar.add(menu);

 		// add Pathology menu with a default photo
		menu = new JMenu("Pathology");
		jmItem = new JMenuItem("Show Photos");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Image img = new ImageIcon("images/#1229 Femur.jpg").getImage();
				BasicBackgroundPanel bbp = new BasicBackgroundPanel(
						CSImageEntity.getImageToFitKeepRatio(img, 800,  630));
		        JScrollPane scrollPane = new JScrollPane(bbp);
				tabbedPane.addTab("Bone Defect", scrollPane);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
        	
        });
        menu.add(jmItem);
		menuBar.add(menu);

 		// add Help menu with the About box
		menu = new JMenu("Help");
		
		jmItem = new JMenuItem("About CS Viewer");
        jmItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// update version number ==> to v1.1 now!
				JDialog aboutBox = new JDialog(CSViewerMain.this, "About CS Viewer - Version 1.1");
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
				ImageIcon flashIcon = new ImageIcon("images/AboutBoxNoVersion.png");
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
                	updateMeasureData(an); 
                	// Update Measure information
               	
                	//Update general information
                	updateSummaryData(an);
                	//System.out.println(an);
                }
            }

        });
    }
    
	public void updateMeasureData(AnimalFamilyInfo an) {
		String testTattoo = an.getAnimalId();
    	System.out.println("||" + testTattoo + "||");
    	/*
    	 * // both have "" in text file
		if (testTattoo.charAt(0) == '"')
			testTattoo = testTattoo.substring(1, testTattoo.length()-1);
		*/
    	//DataFrame<Object> df = this.bmMgr.getMeasureByTattoo(testTattoo);
     	//if(df != null)
    	//	mPanel.editTable(df);
    	//else
    	//	mPanel.editTable(MeasureBean.getTestBean());
    	
    	// let the panel to deal with df, whether null or not	
    	mPanel.editTable(bmMgr.getMeasureByTattoo(testTattoo));
		repaint();	
	}

	private void updateSummaryData(AnimalFamilyInfo an) {
		bPanel.editData(an);
		System.out.println("in update sum: " + an.getGender());
		repaint();
	}
	
	public void updateSummaryData(String testTattoo) {
		AnimalFamilyInfo an = (AnimalFamilyInfo) 
				fmgr.getMatrilTreeNodeById(testTattoo).getUserObject();
			bPanel.editData(an);
			repaint();	
	}

	public SearchMgr getSearchMgr() {
		// TODO Auto-generated method stub
		return sMgr;
	}

	public BoneMeasureMgr getBoneMeasureManager() {
		// TODO Auto-generated method stub
		return this.bmMgr;
	}

}
