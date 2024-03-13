package gui.search;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import mgr.search.SearchMgr;

public class AnimalSetSelectionPanel extends JPanel {
	private SearchMgr sMgr;
	
	public AnimalSetSelectionPanel() {
		this(null);
	}
	
	public AnimalSetSelectionPanel(SearchMgr sMgr) {
		this.sMgr = sMgr;
		
		setLayout(null);
		TitledBorder blockBorder = 
				BorderFactory.createTitledBorder("Family Selection");
		FamilySelectionPanel fp = new FamilySelectionPanel(sMgr);
		fp.setBorder(blockBorder);
		fp.setBounds(10, 10, 320, 510);
		add(fp);
		SeasonSelectionPanel sp = new SeasonSelectionPanel();
		blockBorder = 
				BorderFactory.createTitledBorder("Season Selection");
		sp.setBorder(blockBorder);
		sp.setBounds(10 + 320 + 10, 10, 450, 510);
		add(sp);
	}

	public static void main(String args[]) {
		JFrame f = new JFrame("Animal Selection Test");
		AnimalSetSelectionPanel p = new AnimalSetSelectionPanel();
		f.add(p);
		//f.pack();
		f.setSize(820, 570);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
