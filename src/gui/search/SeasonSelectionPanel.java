package gui.search;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class SeasonSelectionPanel extends JPanel {
	private JList seasonList;
	private JTextField beginSeason;
	private JTextField endSeason;
	//private ButtonGroup genderGroup;
	//private ButtonGroup rangeGroup;

	private JRadioButton jrbBefore, jrbAfter, jrbFemale, jrbMale;
	private Dimension defaultSize = new Dimension(450, 510);
	
	private  TitledBorder blockBorder;
	private JRadioButton jrbBoth;
	private JRadioButton jrbNA;
	
	public SeasonSelectionPanel() {
		this.setMinimumSize(defaultSize);
		this.setMaximumSize(defaultSize);
		this.setPreferredSize(defaultSize);
		this.setLayout(new GridLayout(1, 2));
		loadSeasons();
		initGui();
	}

	private void initGui() {
		JScrollPane sp = new JScrollPane(seasonList);
		//sp.setBounds(25, 25, 25, 25);
		this.add(sp);
		JPanel selPanel = new JPanel();
		loadSelPanel(selPanel);
	    this.add(selPanel); 
	}

	private void loadSelPanel(JPanel selPanel) {
		//selPanel.setLayout(new BoxLayout(selPanel, BoxLayout.Y_AXIS));
		selPanel.setLayout(new GridLayout(4, 2));
		JPanel temp = new JPanel();
		// TODO Auto-generated method stub
		beginSeason = new JTextField(10);
		temp.add(beginSeason);
		selPanel.add(temp);
		temp = new JPanel();
		temp.add(new JLabel("Begin")); 
		selPanel.add(temp);
		
		JPanel rangePanel = createRangeP();
		selPanel.add(rangePanel);
		temp = new JPanel();
		temp.add(new JLabel("Range")); 
		selPanel.add(temp);
		
		temp = new JPanel();
		endSeason = new JTextField(10);
		temp.add(endSeason);
		selPanel.add(temp);
		temp = new JPanel();
		temp.add(new JLabel("End")); 
		selPanel.add(temp);
		
		JPanel genderPanel = createGenderP();
		selPanel.add(genderPanel);
		temp = new JPanel();
		temp.add(new JLabel("Gender")); 
		selPanel.add(temp);
	}

	private JPanel createGenderP() {
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		blockBorder = BorderFactory.createTitledBorder(loweredbevel, "Set gender selection:");
		JPanel p = new JPanel();
		p.setBorder(blockBorder);
		blockBorder.setTitleJustification(TitledBorder.LEFT);
		blockBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		p.setLayout(null);
		this.jrbBoth = new JRadioButton("Both");
		this.jrbFemale = new JRadioButton("Female");
		this.jrbMale = new JRadioButton("Male");
		this.jrbBoth.setBounds(30, 20, 80, 20);
		this.jrbFemale.setBounds(30, 50, 80, 20);
		this.jrbMale.setBounds(30, 80, 80, 20);
		ButtonGroup bg = new ButtonGroup();   
		bg.add(jrbBoth);
		bg.add(jrbFemale);
		bg.add(jrbMale);
		jrbBoth.setSelected(true);
		p.add(jrbBoth);
		p.add(jrbFemale);
		p.add(jrbMale);
		//p.add(new JLabel("Female Male"));
		return p;
	}

	private JPanel createRangeP() {
		JPanel p = new JPanel();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		blockBorder = BorderFactory.createTitledBorder(loweredbevel, "Set range as:");
		blockBorder.setTitleJustification(TitledBorder.LEFT);
		blockBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		p.setBorder(blockBorder);
		p.setLayout(null);
		this.jrbNA = new JRadioButton("NA");
		this.jrbBefore = new JRadioButton("Before");
		this.jrbAfter = new JRadioButton("After");
		this.jrbNA.setBounds(30, 20, 80, 20);
		this.jrbBefore.setBounds(30, 50, 80, 20);
		this.jrbAfter.setBounds(30, 80, 80, 20);
		ButtonGroup bg = new ButtonGroup();   
		bg.add(jrbNA);
		bg.add(jrbBefore);
		bg.add(jrbAfter);
		jrbNA.setSelected(true);
		p.add(jrbNA);
		p.add(jrbBefore);
		p.add(jrbAfter);
		//p.add(new JLabel("Before After"));
		return p;
	}

	private void loadSeasons() {
		ArrayList<String> allSeasons = new ArrayList<String>();
		for (int yr = 1955; yr < 2020; yr++) {
			allSeasons.add("" + yr);
		}
		
		seasonList = new JList(allSeasons.toArray(new String[allSeasons.size()]));
		this.seasonList.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
		          JList list = (JList) evt.getSource();
		          if (evt.getClickCount() == 2) { // Double-click
		            int index = list.locationToIndex(evt.getPoint());
		            beginSeason.setText(list.getSelectedValue().toString());
		          } 
		        }
		      });
	}

	public static void main(String args[]) {
		JFrame f = new JFrame("Season Selection Test");
		SeasonSelectionPanel p = new SeasonSelectionPanel();
		f.add(p);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
