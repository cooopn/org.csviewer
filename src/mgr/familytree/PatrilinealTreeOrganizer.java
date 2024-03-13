package mgr.familytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;

import mgr.AnimalFamilyInfo;

public class PatrilinealTreeOrganizer {
	PatrilinealTreeGenerator ptGen = new PatrilinealTreeGenerator();
		
	// for all tree nodes, lookup by animal id
	// each node leads to a subtree starting from it
   	Map<String, DefaultMutableTreeNode> idToNodeLookup = 
   			new HashMap<String, DefaultMutableTreeNode>(); 

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("CS Patril Trees");

	public PatrilinealTreeOrganizer()  {
		ptGen.loadData();
		//buildTree();
		buildTreeIdToNodeMap();
	}

	private void buildTreeIdToNodeMap() {
		DefaultMutableTreeNode dadNode, childNode, subTreeRoot;
		int chdCount;
		List<AnimalFamilyInfo> childrenList;
		AnimalFamilyInfo aFamInfo;
		
		for (String dadId : ptGen.chronicalSireSet) {
			chdCount = 0;
			aFamInfo = ptGen.getAnimalIdFamilyInfoLookup(dadId);
				
			dadNode = new DefaultMutableTreeNode(aFamInfo);
			if (aFamInfo.getDadID().trim().equals("")) {
				root.add(dadNode);
				idToNodeLookup.put(dadId, dadNode);
			}
			/*
			else {
				subTreeRoot = idToNodeLookup.get(aFamInfo.getDadId());
				subTreeRoot.add(dadNode);
			}
			*/	
							
			//System.out.print(dadId);
			if (dadId.equals("88C"))
				System.out.print("--matched--");
			childrenList = ptGen.dadChildrenLookup.get(dadId);
			for (AnimalFamilyInfo afInfo : childrenList) {
				chdCount++;
				childNode = new DefaultMutableTreeNode(afInfo);
				subTreeRoot = idToNodeLookup.get(afInfo.getDadID());
				subTreeRoot.add(childNode);
				
				//if (ptGen.chronicalSireSet.contains(afInfo.getAnimalId()))
					idToNodeLookup.put(afInfo.getAnimalId(), childNode);
				
			}
			//System.out.println("x" + chdCount + "==>" + dadNode.getChildCount());
		}
	}

	private void buildTree() {
		DefaultMutableTreeNode dadNode, childNode;
		int chdCount;
		
		List<AnimalFamilyInfo> childrenList;
		
		for (String dadId : ptGen.chronicalSireSet) {
			chdCount = 0;
			dadNode = new DefaultMutableTreeNode(ptGen.getAnimalIdFamilyInfoLookup(dadId));
			root.add(dadNode);
			idToNodeLookup.put(dadId, dadNode);
			
			System.out.print(dadId);
			childrenList = ptGen.dadChildrenLookup.get(dadId);
			if (childrenList == null) {
				continue;
			}
			//else if (idToNodeLookup.get(dadId) == null) {
			//	dadNode = new DefaultMutableTreeNode(ptGen.getAnimalIdFamilyInfoLookup(dadId));
				
			//}
			
			// to be replaced with a recursive method to handle subtrees
				for (AnimalFamilyInfo afInfo : childrenList) {
					chdCount++;
					childNode = new DefaultMutableTreeNode(afInfo);
					dadNode.add(childNode);
					idToNodeLookup.put(afInfo.getAnimalId(), dadNode);
				}
			
			System.out.println("x" + chdCount + "==>" + dadNode.getChildCount());
		}
	}



	public DefaultMutableTreeNode getRoot() {
		// TODO Auto-generated method stub
		return root;
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
