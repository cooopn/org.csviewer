/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csdb;

import java.awt.Color;

import csdb.core.CircleNode;
import csdb.core.Edge;
import csdb.core.Graph;
import csdb.core.LineEdge;
import csdb.core.Node;

/**
 *
 * @author zhao_m
 */
public class VtGraph extends Graph {
   public Node[] getNodePrototypes()
   {
      Node[] nodeTypes =
         {
            new IconNode("image/CloudIcon.png"),
            new IconNode("image/HostIcon.png"),
            new IconNode("image/ClusterIcon.png"),
            new IconNode("image/RouterIcon.png"),
            new IconNode("image/PrinterIcon.png"),
            new IconNode("image/FirewallIcon.png"),
            new IconNode("image/SwitchIcon.png"),
            new CircleNode(Color.WHITE)
         };
      return nodeTypes;
   }

   public Edge[] getEdgePrototypes()
   {
      Edge[] edgeTypes =
         {
            new LineEdge()
         };
      return edgeTypes;
   }
}
