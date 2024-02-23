package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import joinery.DataFrame;
import search.SearchMgr;

public class FamilySelectionPanel extends JPanel {
	private DefaultListModel fSelectionModel = 
			new DefaultListModel();
	//private DefaultListModel sSelectionModel = 
	//		new DefaultListModel();
	private Dimension defaultSize = new Dimension(320, 510);
	private JList familyList;
	private JList selectedList;
	private JTextArea selectedLabel;
	
	private SearchMgr sMgr;

	public FamilySelectionPanel() {
		this(null);
	}

	public FamilySelectionPanel(SearchMgr sMgr) {
		this.sMgr = sMgr;
		this.setMinimumSize(defaultSize);
		this.setMaximumSize(defaultSize);
		this.setPreferredSize(defaultSize);
		this.setLayout(new GridLayout(1, 2));
		loadFamilies();
		initGui();
	}

	private void initGui() {
		JPanel leftColumn = new JPanel();
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
		JLabel allFamilies = new JLabel("All Families:");
		leftColumn.add(allFamilies);
		JScrollPane sp = new JScrollPane(familyList);
		//sp.setBounds(25, 25, 25, 25);
		leftColumn.add(sp);
		this.add(leftColumn);
		
		JPanel selPanel = new JPanel();
	    //selPanel.setLayout(new GridLayout(2, 1));
		//selPanel.setBounds(25, 25, 25, 25);
	    selPanel.setLayout(new BoxLayout(selPanel, BoxLayout.Y_AXIS));
		JLabel selFamilies = new JLabel("Selected Families:");
		selFamilies.setAlignmentX(CENTER_ALIGNMENT);
		selPanel.add(selFamilies);
	    selectedList = new JList(fSelectionModel);
	    selectedList.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
	          JList list = (JList) evt.getSource();
	          if (evt.getClickCount() == 2) { // Double-click
	            int index = list.locationToIndex(evt.getPoint());
	            fSelectionModel.remove(index);
	          }
	        }
	    });
	    selectedList.setMinimumSize(new Dimension(190, 200));
	    selectedList.setMaximumSize(new Dimension(190, 200));
	    selectedList.setPreferredSize(new Dimension(190, 200));
	    selectedList.setBorder(new LineBorder(Color.BLACK));
	    selPanel.add(selectedList);

	    selectedLabel = new JTextArea(5, 40);
	    selectedLabel.setLineWrap(true);
	    selectedLabel.setMinimumSize(new Dimension(190, 100));
	    selectedLabel.setMaximumSize(new Dimension(190, 100));
	    selectedLabel.setPreferredSize(new Dimension(190, 100));
	    selectedLabel.setText("Please select...");
		selPanel.add(selectedLabel);
		
		JPanel controlPanel = new JPanel();
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fSelectionModel.clear();
				//repaint();
			}
			
		});
		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSelectedFamilies();
			}

			private void setSelectedFamilies() {
				System.out.println("SearchMgr called: " + 
						getSelectFamilies().length + " families (" + 						
						sMgr.selectAnimalByFounderIds(getSelectFamilies()).length() + ")");
				System.out.println(fSelectionModel.elements().nextElement() +
						"(" + fSelectionModel.getSize() + ")");
			}
			
		});		controlPanel.add(clearButton);
		controlPanel.add(selectButton);
		selPanel.add(controlPanel);
		
		selPanel.add(Box.createVerticalGlue()); 
		
		JPanel p = new JPanel();
		p.add(new JButton("Select all families"));
		selPanel.add(p);
		
		// add right column to the panel
	    this.add(selPanel); 
	}

	private void loadFamilies() {
		try {
			DataFrame<Object> df = DataFrame.readCsv("data/FounderCode.csv");
			familyList = new JList(df.col("MatrilCode").
					toArray(new String[df.col("MatrilCode").size()]));
			familyList.addMouseListener(new MouseAdapter() {
		        public void mouseClicked(MouseEvent evt) {
		          JList list = (JList) evt.getSource();
		          if (evt.getClickCount() == 2) { // Double-click
		            int index = list.locationToIndex(evt.getPoint());
		            if (!fSelectionModel.contains(list.getSelectedValue())) {
		            	fSelectionModel.addElement(list.getSelectedValue());
		            	selectedLabel.setText("A new family selected.");
		            }
		            else
		            	selectedLabel.setText("Already selected.");
		            selectedList.setModel(fSelectionModel);
		          } 
		          else {
		        	  selectedLabel.setText("Please double-click to select...");
		          }
		        }
		      });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String[] getSelectFamilies() {
		String[] results = new String[fSelectionModel.toArray().length];
		for (int i = 0; i < fSelectionModel.toArray().length; i++)
			results[i] = fSelectionModel.toArray()[i].toString();
		//System.out.println(fSelectionModel.toArray());
		return results;
	}
	
	public static void main(String args[]) {
		JFrame f = new JFrame("Family Selection Test");
		FamilySelectionPanel p = new FamilySelectionPanel();
		f.add(p);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		JButton jbtnTest = new JButton("Show Families");
		jbtnTest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.selectedLabel.setText("" + p.getSelectFamilies().length);
				
			}
			
		});
		f.add(jbtnTest, BorderLayout.SOUTH);
	}
}
