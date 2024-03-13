package mgr.familytree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//import java.util.TreeSet;

import javax.swing.tree.DefaultMutableTreeNode;

import mgr.AnimalFamilyInfo;

/*
 * Holding a MatrilinealTreeGenerator to build a JTree model with
 * complete animal information
 */
public class MatrilinealTreeOrganizer {
	MatrilinealTreeGenerator mtGen = new MatrilinealTreeGenerator();
	
    List<AnimalFamilyInfo> nodesInTree; // for now
	
    // to be loaded from a file
	LinkedList<AnimalFamilyInfo> founderList = new LinkedList<AnimalFamilyInfo>();
	//HashMap<String, LinkedList<AnimalFamilyInfo>> momChildrenLookup = new HashMap();
	
	// for all tree nodes, lookup by animal id
	// each node leads to a subtree starting from it
   	Map<String, DefaultMutableTreeNode> idToNodeLookup = 
   			new HashMap<String, DefaultMutableTreeNode>(); 


	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("CS Matril Trees");;

	public MatrilinealTreeOrganizer()  {
		try {
			mtGen.doIt();
			
			Scanner input = new Scanner(new File("data/FounderCode.csv"));
			String[] parts;
			while (input.hasNextLine()) {
				parts = input.nextLine().split(",");
				if (parts[2].equals("FounderId"))
					continue;
				founderList.add(new AnimalFamilyInfo(parts[1], parts[2]));
													//founderId, subjId
													//eg: Q1000, AC
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nodesInTree = mtGen.rows;
		//founderList = new LinkedList<String>(new TreeSet<String>(mtGen.momMatrLookup.values()));
		
		buildTree();
	}

	private void buildTree() {
		  int noParentCount = 0;
		  List<AnimalFamilyInfo> misplacedNodes = new LinkedList<AnimalFamilyInfo>();
		  //List<String> misplacedNodes = new LinkedList<String>();
		
		addFoundersToTree();
	  	DefaultMutableTreeNode curNode, parentNode = null;
	  	//Map<String, DefaultMutableTreeNode> idToNodeLookup = 
	  	//		new HashMap<String, DefaultMutableTreeNode>();
	  	
		System.out.println("Nodes" + nodesInTree.size()); 	
	  	String momId, animalId;  
	  	for (AnimalFamilyInfo node : nodesInTree) {
	  		momId = node.getMomId();
	  		animalId = node.getAnimalId();
	  		//System.out.println(animalId + "[" + momId +"]");
	  		curNode = new DefaultMutableTreeNode(node);
	  		if (momId.length() == 0) {
	  			//founderList.add(animalId); 
	  			System.out.println("shouldn't be printed!");
	  			root.add(curNode);
	  		}
	  		else {
	  			parentNode = idToNodeLookup.get(momId);
	  			if (parentNode == null) {
	  				misplacedNodes.add(node);
	  				//misplacedNodes.add(node.toStringFullForm());
	  				noParentCount++;
	  				continue;
	  			}
	  			parentNode.add(curNode);
	  		}
	  	        
	  		idToNodeLookup.put(animalId, curNode);
	  		//System.out.println(animalId + " [" + momId + "]");
	  	}
	  	
	  	/*
	      tree.setCellRenderer(new AnimalNodeRenderer());

	      //tree.setRootVisible(false);
	      tree.setShowsRootHandles(true);
			System.out.println("Misplaced nodes: " + misplacedNodes.size());
			
			DefaultMutableTreeNode momNode;
			for (AnimalNode node : misplacedNodes) {
				System.out.println(node.toStringFullForm());
				///* try again to merge the founder tree file with the missing nodes
				//reenter the missing nodes in JTree
				curNode = new DefaultMutableTreeNode(node);
				momNode = idToNodeLookup.get(node.momId);
				momNode.add(curNode);
				System.out.println("Nodes " + curNode + "==>" + momNode);
		  		idToNodeLookup.put(node.animalId, curNode);
		  		System.out.println(node.animalId);
		  		//* /
			}
	    	*/
		System.out.println("Misp" + misplacedNodes.size()); 	
	}

	private void addFoundersToTree() {
		DefaultMutableTreeNode founderNode;
		for (AnimalFamilyInfo founderEntry : founderList) {
			founderNode = new DefaultMutableTreeNode(founderEntry);
			root.add(founderNode);
			idToNodeLookup.put(founderEntry.getAnimalId(), founderNode);
			//System.out.println("adding founder" + founderEntry + "==>" + founderNode);
			
		}
		//System.out.println(idToNodeLookup.keySet());
	}
	
	public DefaultMutableTreeNode getRoot() {
		return root;
	}
	
	public String[] getFounders() {
		String[] founders = new String[founderList.size()];
		for (int i=0; i<founderList.size(); i++)
				founders[i] = founderList.get(i).getAnimalId();
		return founders;
	}
	/**
	 * Gets the map of tree nodes
	 * @return idToNodeLookup map associating animalId to tree node
	 */
	public Map<String, DefaultMutableTreeNode> getGeneralInfo()
	{
		return idToNodeLookup;
	}

	public DefaultMutableTreeNode getNodeById(String animalId) {
		// TODO Auto-generated method stub
		return idToNodeLookup.get(animalId);
	}
}
