/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csdb;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

import csdb.core.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Martin
 */
public class IconNode implements Node {
   private double x;
   private double y;
   private double width, height;
   protected ImageIcon icon;

   protected IconNode() {

   }

   public IconNode(String iconName) {
       this(new ImageIcon(iconName));
    }

   public IconNode(ImageIcon icon) {
       this.icon = icon;
       x = 0;
       y = 0;
       width = icon.getIconWidth();
       height = icon.getIconHeight();
   }

    public boolean contains(Point2D aPoint) {
      Ellipse2D circle = new Ellipse2D.Double(
            x, y, width, height);
      return circle.contains(aPoint);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(icon.getImage(), (int)x, (int)y, null);
                //(int)(x - width/2), (int)(y - height/2), null);
    }

    public Rectangle2D getBounds() {
      return new Rectangle2D.Double(
            x, y, width, height);
    }

    public Point2D getConnectionPoint(Point2D aPoint) {
      double centerX = x + width / 2;
      double centerY = y + height / 2;
      double dx = aPoint.getX() - centerX;
      double dy = aPoint.getY() - centerY;
      double distance = Math.sqrt(dx * dx + dy * dy);
      if (distance == 0) return aPoint;
      else return new Point2D.Double(
            centerX + dx * (width / 2) / distance,
            centerY + dy * (height / 2) / distance);
    }

    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public Object clone() {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException exception)
      {
         return null;
      }
    }

    public Point2D getIconCenter() {
        return new Point2D.Double(x + width / 2, y + height / 2);
    }
}
