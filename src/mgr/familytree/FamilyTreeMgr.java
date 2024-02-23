package mgr.familytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import mgr.AnimalFamilyInfo;

public class FamilyTreeMgr {
    private DefaultMutableTreeNode patrilRoot;
    private DefaultMutableTreeNode matrilRoot;
    private MatrilinealTreeOrganizer matrilTreeOrg;
    private PatrilinealTreeOrganizer patrilTreeOrg;

	public FamilyTreeMgr() {
		matrilTreeOrg = new MatrilinealTreeOrganizer();
		patrilTreeOrg = new PatrilinealTreeOrganizer();
	}
	
	public JTree getMatrilTree() {
		JTree tree = new JTree(matrilTreeOrg.getRoot());
		return tree;
	}

	public JTree getPatrilTree() {
		JTree tree = new JTree(patrilTreeOrg.getRoot());
		return tree;
	}

	public JTree getMatrilTree(String trim) {
		// get a subtree from a founder id
		return null;
	}

	public JTree getPatrilTree(String trim) {
		// get a subtree from a father id
		return null;
	}

	public DefaultMutableTreeNode getTestNode() {
		return (DefaultMutableTreeNode) matrilTreeOrg.getRoot().
				getChildAt(0).getChildAt(0);
	}

	public DefaultMutableTreeNode getMatrilTreeNodeById(String animalId) {
		if (animalId.charAt(0) != '"')
			animalId = '"' + animalId + '"';
		DefaultMutableTreeNode node = matrilTreeOrg.getNodeById(animalId);
		return node;
	}

	public DefaultMutableTreeNode getPatrilTreeNodeById(String animalId) {
		if (animalId.charAt(0) != '"')
			animalId = '"' + animalId + '"';
		DefaultMutableTreeNode node = patrilTreeOrg.getNodeById(animalId);
		return node;
	}
	
	public JTree getMatrilTree(String founderCode, List<String> descendants) {
		Map<String, DefaultMutableTreeNode> selectedTreeToFounder = new TreeMap<>();
		DefaultMutableTreeNode founderAsRoot;
		DefaultMutableTreeNode root = getMatrilTreeNodeById(founderCode);
		founderAsRoot = new DefaultMutableTreeNode(root.getUserObject());
		
		selectedTreeToFounder.put(((AnimalFamilyInfo) 
				founderAsRoot.getUserObject()).getAnimalId(), founderAsRoot);
		JTree tree = new JTree(founderAsRoot);
		DefaultMutableTreeNode descNode=null;
		DefaultMutableTreeNode nodeInAllTree;
/*		
		// assuming only two nodes: 299 and 521
		descNode = new DefaultMutableTreeNode(
				getMatrilTreeNodeById(descendants.get(0)).getUserObject());
		selectedTreeToFounder.put(((AnimalFamilyInfo) 
				descNode.getUserObject()).getAnimalId(), descNode);
		System.out.println(descNode.getUserObject());
		if (!founderAsRoot.isNodeDescendant(descNode))
			founderAsRoot.add(descNode);
*/
		TreePath path1;
		AnimalFamilyInfo curSubj;
		for (int desc=0; desc<descendants.size(); desc++) {
			path1 = getPath(root, 
					getMatrilTreeNodeById(descendants.get(desc)));
			System.out.println("Desc #" + desc + ":" + path1.getPathCount());
			DefaultMutableTreeNode newNode;
			for (int i=0; i<path1.getPathCount(); i++) {
				nodeInAllTree = (DefaultMutableTreeNode) 
						path1.getPathComponent(i);
				curSubj = (AnimalFamilyInfo) (nodeInAllTree.getUserObject());
				String animalId = curSubj.getAnimalId();
				System.out.println(i + "|" + animalId);
				if (selectedTreeToFounder.get(animalId) == null)  
				{
					newNode = new DefaultMutableTreeNode(
							nodeInAllTree.getUserObject());
					if (i==0)
						founderAsRoot.add(newNode);
					else
						descNode.add(newNode);
						
					selectedTreeToFounder.put(animalId, newNode);
					descNode = newNode;
				}
				else {
					descNode = selectedTreeToFounder.get(animalId);
					System.out.println("Node " + animalId + " already in selected tree");
				}
			}
			System.out.println();
		}

		return tree;
	}

	private void printPath(TreePath path1) {
		for (int i=0; i<path1.getPathCount(); i++) {
			System.out.println(path1.getPathComponent(i));
		}		
	}

	TreePath getPath(TreeNode founderNode, TreeNode treeNode) {
	    List<Object> nodes = new ArrayList<Object>();
	    if (treeNode != null) {
	      nodes.add(treeNode);
	      treeNode = treeNode.getParent();
	      //while (treeNode != null) {
		  while (treeNode != null && !treeNode.equals(founderNode)) {
	        nodes.add(0, treeNode);
	        treeNode = treeNode.getParent();
	      }
	    }

	    return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
	}
}

