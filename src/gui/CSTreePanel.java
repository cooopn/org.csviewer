package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import main.CSViewerMain;
import mgr.AnimalFamilyInfo;
//import mgr.MeasureBean;

public class CSTreePanel extends JPanel {
    private JTree tree;
    private CSViewerMain window;

	public CSTreePanel(JTree tree, CSViewerMain window) {
		setLayout(new BorderLayout());
		//theTreePane = new JScrollPane(tree);
		//theTreePane.setPreferredSize(new Dimension(200, 200));
		
		this.window = window;

		this.tree = tree;
		JScrollPane theTreePane = new JScrollPane(tree);
    	theTreePane.setPreferredSize(new Dimension(200, 200));
        add(theTreePane, BorderLayout.CENTER);
		
        tree.setCellRenderer(new AnimalNodeRenderer());
        //tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
	}

  	class AnimalNodeRenderer extends DefaultTreeCellRenderer {
        Icon nodeIcon;
        final public ImageIcon FEMALE_ICON  = new ImageIcon("images/female_icon.png");
        final public ImageIcon MALE_ICON  = new ImageIcon("images/male_icon.png");
        final public ImageIcon UNKNOWN_ICON  = new ImageIcon("images/unknown_icon.png");
        final public ImageIcon FOUNDER_ICON  = new ImageIcon("images/founder_icon.png");

        public AnimalNodeRenderer() {
        }

        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);
            String text; 
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)value;
            Object userObject = node.getUserObject();
            if (userObject instanceof AnimalFamilyInfo) {
              	
            	  AnimalFamilyInfo nodeInfo = (AnimalFamilyInfo)userObject;
            	  //Unicode is messy now
            	  //text = nodeInfo.getUnicode();
            	  //change after cleaning up
            	  text = nodeInfo.getAnimalId();
            	  String idWithoutQuote = nodeInfo.getAnimalId().substring(1, 
            			  nodeInfo.getAnimalId().length()-1);
	            if (hasMeasure(idWithoutQuote)) {
	            	text = text + "*";
	            }
            }
            else
          	  text = value.toString();
            
            nodeIcon = setNodeIcon(value);
            setIcon(nodeIcon);
            setToolTipText(text);
            setText(text);
            //System.out.println((value.getClass()) + "?Text to render..." + text);

            return this;
        }

        private boolean hasMeasure(String animalId) {
			// return false now
			//return window.getMeasureManager().hasMeasure(animalId);
        	return false;
		}

		private Icon setNodeIcon(Object value) {
        	Icon retIcon;
        	
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)value;
            if (node.getUserObject() instanceof String)
            	retIcon = null; // handles dummy root node
            else {
          	  AnimalFamilyInfo nodeInfo =
	                    (AnimalFamilyInfo)(node.getUserObject());
          	  //System.out.println(nodeInfo.getUnicode()  + "{" + nodeInfo.getGender().charAt(0) + "}");
          	  
	            String gender = nodeInfo.getGender();
	            //if (nodeInfo.getMomId().length() == 0) {
	  	        if (nodeInfo.getMomId()==null) {
	            	retIcon = FOUNDER_ICON;
	            }
	  	        // gender is "f" or "m"!!!
	            else if (gender.charAt(1)=='f') {
	            	retIcon = FEMALE_ICON;
	            }
	            else if (gender.charAt(1)=='m') {
	            	retIcon = MALE_ICON;
	            } else {
	            	retIcon = UNKNOWN_ICON;
	            } 
            }

            return retIcon;
		}
  }
}
