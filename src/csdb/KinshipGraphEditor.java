package csdb;

import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;

import csdb.core.Graph;
import csdb.core.GraphFrame;
import csdb.core.LineEdge;
//import dsci.AnimalFounderPathBuilder;
//import dsci.AnimalFounderPathBuilder.Dyad;

public class KinshipGraphEditor {
	AnimalFounderPathBuilder builder;
	KinshipGraph kinGraph = new KinshipGraph();
	int xCenter = 300, y0 = 10, hl = 50;
	
	   public static void main(String[] args)
	   {
		   KinshipGraphEditor kgEd = new KinshipGraphEditor();
		   kgEd.buildGraphforAnimal("\"0B0\"", 3, true);
		   //kgEd.buildGraphforAnimal("\"96V\"", 3, true);
		   //kgEd.kinGraph =  kgEd.buildDefaultGraph();
	      JFrame frame = new GraphFrame(kgEd.kinGraph);
	      frame.setVisible(true);
	   }

	public void buildGraphforAnimal(String tattoo, int level, 
			boolean founder) {
		AnimalNode aNode = null;
		if (tattoo != null) {
			aNode = new AnimalNode(tattoo);
			kinGraph.add(aNode, new Point(xCenter - 20, y0));
		}
		DyadNode[][] ancestors;
		AnimalNode[] founders=null;
		
		if (level > 0) {
			ancestors = new DyadNode[level][];
			for (int i=0; i<level; i++) {
				ancestors[i] = new DyadNode[(int)Math.pow(2, i)];
			}
			
			if (founder)
				founders = new AnimalNode[(int)Math.pow(2, level)];
		}
		else
			return;
		
		AnimalFounderPathBuilder.Dyad d;
		DyadNode dNode;
		builder = new AnimalFounderPathBuilder();
		builder.build();
		d = builder.getDyadForChild(tattoo);
		dNode = new DyadNode(d.getDam(),d.getSire());
		ancestors[0][0] = dNode;
		kinGraph.add(dNode, new Point(xCenter - 40, y0 + hl)); // dyad node offset more
		LineEdge lEdge = new LineEdge();
		lEdge.connect(aNode, dNode);
		kinGraph.connect(lEdge, aNode.getIconCenter(), dNode.getIconCenter());
		
		System.out.println("Parent for " + tattoo + " are " + d);
		
		String dam, sire;
		int[][] dyadOffset = {{0}, {-55, 125}, {-130, -50, 120, 200}};
		for (int l=0; l<level-1; l++) {
			for (int n=0; n<ancestors[l].length; n++) {
				if (ancestors[l][n] == null)
					continue;

				dam = ancestors[l][n].getDam();
				sire = ancestors[l][n].getSire();
				d = builder.getDyadForChild(dam);
				if (d == null)
					continue;
				System.out.println("**Parent for " + dam + " is " + d);
				dNode = new DyadNode(d.getDam(),d.getSire());
				kinGraph.add(dNode, new Point(xCenter - 40 - 80 + dyadOffset[l+1][2*n],
						y0 + hl * (l+2)));
				ancestors[l+1][2*n] = dNode;
				lEdge = new LineEdge();
				lEdge.connect(ancestors[l][n], ancestors[l+1][2*n]);
				kinGraph.connect(lEdge, ancestors[l][n].getIconCenter(), 
						ancestors[l+1][2*n].getIconCenter());
				d = builder.getDyadForChild(sire);
				if (d == null)
					continue;
				System.out.println("**Parent for " + sire + " is " + d);
				dNode = new DyadNode(d.getDam(),d.getSire());
				kinGraph.add(dNode, new Point(xCenter - 40 - 20 + dyadOffset[l+1][2*n+1],
						 y0 + hl * (l+2)));
				ancestors[l+1][2*n+1] = dNode;
				lEdge = new LineEdge();
				lEdge.connect(ancestors[l][n], ancestors[l+1][2*n+1]);
				kinGraph.connect(lEdge, ancestors[l][n].getIconCenter(), 
						ancestors[l+1][2*n+1].getIconCenter());
				/*
				System.out.println("Parent for " + dam + " are " + 
				ancestors[l+1][2*n]);
				System.out.println("Parent for " + sire + " are " + 
				ancestors[l+1][2*n+1]);
				*/
			}
		}

//
		System.out.println("Level = " + level + " and length = " + 
				ancestors[level-1].length);

		FounderQuickLink fLink;
		Map<String, AnimalNode> uniqueFounders = new TreeMap<>();
		String founderCode;
		if (founder) {
			for (int f=0; f<ancestors[level-1].length; f++) {
				if (ancestors[level-1][f] == null)
					continue;
				//System.out.println("f = " + f); 
				dam = ancestors[level-1][f].getDam();
				if (dam != null && dam.length() > 0) {
					founderCode = builder.getFounderForAnimal(dam);
					aNode = uniqueFounders.get(founderCode);
					if (aNode == null) {
						aNode = new AnimalNode(founderCode);
						uniqueFounders.put(founderCode, aNode);
						founders[2*f] = aNode;
						kinGraph.add(aNode, new Point(50 + 2*f * 55, 250));
					}
					fLink = new FounderQuickLink();
					fLink.connect(ancestors[level-1][f], aNode);
					System.out.println("Connecting " + ancestors[level-1][f].getDam() + 
							" and " + aNode.getTattoo() + " @" +f);
					kinGraph.connect(fLink, ancestors[level-1][f].getIconCenter(), 
							aNode.getIconCenter());
				}
				sire = ancestors[level-1][f].getSire();
				if (sire != null && sire.length() > 0) {
					founderCode = builder.getFounderForAnimal(sire);
					aNode = uniqueFounders.get(founderCode);
					if (aNode == null) {
						aNode = new AnimalNode(founderCode);
						uniqueFounders.put(founderCode, aNode);
						founders[2*f+1] = aNode;
						kinGraph.add(aNode, new Point(50 + (2*f + 1) * 55, 250));
					}
					fLink = new FounderQuickLink();
					fLink.connect(ancestors[level-1][f], aNode);
					System.out.println("Connecting " + ancestors[level-1][f].getSire() + 
							" and " + aNode.getTattoo() + " @" +f);
					kinGraph.connect(fLink, ancestors[level-1][f].getIconCenter(), 
							aNode.getIconCenter());
				}

				//System.out.println("Founder for " + dam + " is " + founders[2*f]);
				//founders[2*f+1] = builder.getFounderForAnimal(sire);
				//System.out.println("Founder for " + sire + " is " + founders[2*f+1]);
			}
		}
//		
	}

	private void buildTreeforAnimal(String tattoo, int level, 
			boolean founder) {
		AnimalFounderPathBuilder.Dyad[][] ancestors;
		String[] founders=null;
		
		if (level > 0) {
			ancestors = new AnimalFounderPathBuilder.Dyad[level][];
			for (int i=0; i<level; i++) {
				ancestors[i] = new AnimalFounderPathBuilder.Dyad[(int)Math.pow(2, i)];
			}
			
			if (founder)
				founders = new String[(int)Math.pow(2, level)];
		}
		else
			return;
		
		builder = new AnimalFounderPathBuilder();
		builder.build();
		ancestors[0][0] = builder.getDyadForChild(tattoo);
		System.out.println("Parent for " + tattoo + " are " + ancestors[0][0]);
		
		String dam, sire;
		for (int l=0; l<level-1; l++) {
			for (int n=0; n<ancestors[l].length; n++) {
				dam = ancestors[l][n].getDam();
				sire = ancestors[l][n].getSire();
				ancestors[l+1][2*n] = builder.getDyadForChild(dam);
				ancestors[l+1][2*n+1] = builder.getDyadForChild(sire);
				System.out.println("Parent for " + dam + " are " + 
				ancestors[l+1][2*n]);
				System.out.println("Parent for " + sire + " are " + 
				ancestors[l+1][2*n+1]);
			}
		}
		
		if (founder) {
			for (int f=0; f<ancestors[level-1].length; f++) {
				dam = ancestors[level-1][f].getDam();
				sire = ancestors[level-1][f].getSire();
				founders[2*f] = builder.getFounderForAnimal(dam);
				System.out.println("Founder for " + dam + " is " + founders[2*f]);
				founders[2*f+1] = builder.getFounderForAnimal(sire);
				System.out.println("Founder for " + sire + " is " + founders[2*f+1]);
			}
		}
		/*
		DyadNode[] ancestors = new DyadNode[(int)Math.pow(2, level) - 1];
		AnimalNode[] founders = new AnimalNode[(int)Math.pow(2, level)];
		
		//addAnimalNode=tattoo
		AnimalNode aNode = new AnimalNode(tattoo);
		kinGraph.add(aNode, new Point(xCenter - 20, y0));
		
		if (level > 0) {
			Dyad d = builder.getDyadForChild("\"" + tattoo + "\"");
			System.out.println("Parent for " + tattoo + " are " + d);
			DyadNode dNode = new DyadNode(d.getDam(),d.getSire());
			kinGraph.add(dNode, new Point(xCenter - 40, y0 + 50));
			ancestors[0] = dNode;
			LineEdge lEdge = new LineEdge();
			lEdge.connect(aNode, dNode);
			kinGraph.connect(lEdge, aNode.getIconCenter(), dNode.getIconCenter());
			
			
			//add ancestor nodes by level
			for (int i=1; i<=level; i++) {
				for (int j=0; j<(int)Math.pow(2, i-1); j++) {
					
				}
			}
		}
		*/
		//return kinGraph;
	}


	private KinshipGraph buildDefaultGraph() {
		KinshipGraph kg = new KinshipGraph();
		
		// the animal
		AnimalNode aNode = new AnimalNode("0B0");
		kg.add(aNode, new Point(250, 10));

		// its parents
		DyadNode dNode = new DyadNode("Z08", "08N");
		kg.add(dNode, new Point(220, 60));
		LineEdge lEdge = new LineEdge();
		lEdge.connect(aNode, dNode);
		kg.connect(lEdge, aNode.getIconCenter(), dNode.getIconCenter());
		
		// grandparents from both sides
		DyadNode dNode2 = new DyadNode("K11", "I75");
		DyadNode tempNode = dNode2;
		kg.add(dNode2, new Point(150, 110));
		lEdge  = new LineEdge();
		lEdge.connect(dNode, dNode2);
		kg.connect(lEdge, dNode.getIconCenter(), dNode2.getIconCenter());

		dNode2 = new DyadNode("V36", "X81");
		kg.add(dNode2, new Point(300, 110));
		lEdge  = new LineEdge();
		lEdge.connect(dNode, dNode2);
		kg.connect(lEdge, dNode.getIconCenter(), dNode2.getIconCenter());
		
		// aNode now for the founder
		aNode = new AnimalNode("116");
		FounderQuickLink fLink = new FounderQuickLink();
		// great grandparents
		dNode = new DyadNode("J01", "C60");
		kg.add(dNode, new Point(380, 160));
		lEdge = new LineEdge();
		lEdge.connect(dNode2, dNode);
		kg.connect(lEdge, dNode2.getIconCenter(), dNode.getIconCenter());
		// adding founder tracing
		kg.add(aNode, new Point(360, 230));
		fLink.connect(dNode, aNode);
		kg.connect(fLink, dNode.getIconCenter(), aNode.getIconCenter());
		// adding founder tracing
		aNode = new AnimalNode("091");
		fLink = new FounderQuickLink();
		kg.add(aNode, new Point(410, 230));
		fLink.connect(dNode, aNode);
		kg.connect(fLink, dNode.getIconCenter(), aNode.getIconCenter());
		
		dNode = new DyadNode("D69", "");
		kg.add(dNode, new Point(80, 170));
		lEdge = new LineEdge();
		lEdge.connect(dNode2, dNode);
		kg.connect(lEdge, dNode2.getIconCenter(), dNode.getIconCenter());
		// adding founder tracing
		aNode = new AnimalNode("004");
		fLink = new FounderQuickLink();
		kg.add(aNode, new Point(50, 230));
		fLink.connect(dNode, aNode);
		kg.connect(fLink, dNode.getIconCenter(), aNode.getIconCenter());
		
		dNode = tempNode; //(DyadNode) kg.findNode(new  Point(150, 110));
		dNode2 = new DyadNode("D69", "");
		kg.add(dNode2, new Point(50, 160));
		lEdge = new LineEdge();
		lEdge.connect(dNode2, dNode);
		kg.connect(lEdge, dNode2.getIconCenter(), dNode.getIconCenter());
		// adding founder tracing
		fLink = new FounderQuickLink();
		fLink.connect(dNode, aNode);
		kg.connect(fLink, dNode2.getIconCenter(), aNode.getIconCenter());
		
		dNode2 = new DyadNode("A58", "A85");
		kg.add(dNode2, new Point(220, 160));
		lEdge = new LineEdge();
		lEdge.connect(dNode2, dNode);
		kg.connect(lEdge, dNode2.getIconCenter(), dNode.getIconCenter());
		// adding founder tracing
		aNode = new AnimalNode("058");
		fLink = new FounderQuickLink();
		kg.add(aNode, new Point(200, 230));
		fLink.connect(dNode, aNode);
		kg.connect(fLink, dNode2.getIconCenter(), aNode.getIconCenter());
		// adding founder tracing
		aNode = new AnimalNode("RB");
		fLink = new FounderQuickLink();
		kg.add(aNode, new Point(270, 230));
		fLink.connect(dNode, aNode);
		kg.connect(fLink, dNode2.getIconCenter(), aNode.getIconCenter());

		return kg;
	}

	public Graph getKinshipGraph() {
		// TODO Auto-generated method stub
		return this.kinGraph;
	}

}
