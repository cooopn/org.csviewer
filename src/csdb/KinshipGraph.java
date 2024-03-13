package csdb;

import java.awt.Color;

import csdb.core.CircleNode;
import csdb.core.Edge;
import csdb.core.Graph;
import csdb.core.LineEdge;
import csdb.core.Node;

public class KinshipGraph extends Graph {
	   public Node[] getNodePrototypes()
	   {
	      Node[] nodeTypes =
	         {
		        new AnimalNode("Tattoo"),
		        new DyadNode("Dam", "Sire")
		        /*
	            new IconNode("image/AnimalButtonIcon.png"),
	            new IconNode("image/DyadButtonIcon.png"),
	            new CircleNode(Color.WHITE)
	            */
	         };
	      return nodeTypes;
	   }

	   public Edge[] getEdgePrototypes()
	   {
	      Edge[] edgeTypes =
	         {
 	            new LineEdge(),
	            new FounderQuickLink()
	         };
	      return edgeTypes;
	   }
}
