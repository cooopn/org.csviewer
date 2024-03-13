/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csdb;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author zhao_m
 */
public class AnimalNode extends IconNode {
	private String tattoo;
	
   public AnimalNode(String caption) {
       super(new LabelIcon(caption));
       tattoo = caption;
       /*
       System.out.println(caption);
       JOptionPane.showConfirmDialog(null, "See my LabelIcon?",
               "LabelIcon Test", JOptionPane.OK_OPTION,
               JOptionPane.PLAIN_MESSAGE, icon);
               */
   }
   
   public Object clone() {
	   String t = JOptionPane.showInputDialog(null, "Please provide Tattoo...");
	   
	   return new AnimalNode(t);
   }

	public static void main(String[] args) {
	       JOptionPane.showConfirmDialog(null, "See my LabelIcon?",
	               "LabelIcon Test", JOptionPane.OK_OPTION,
	               JOptionPane.PLAIN_MESSAGE, new LabelIcon("59A"));
	       
	       JOptionPane.showConfirmDialog(null, "See my LabelIcon?",
	               "LabelIcon Test", JOptionPane.OK_OPTION,
	               JOptionPane.PLAIN_MESSAGE, new DyadNode("38P", "70D").icon);
   }

	public String getTattoo() {
		// TODO Auto-generated method stub
		return tattoo;
	}
}

class LabelIcon extends ImageIcon {
   private String caption;
    private int width = 40;   // default
    private int height = 25;  // default
   
   public LabelIcon(String caption) {
       this.caption = caption.replace("\"", "");
       Image image = createAnimalImage();
       setImage(image);
   }

   private Image createAnimalImage() {
		BufferedImage image = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		drawAnimalImage(g);
		return image;
	}
	
    public int getIconHeight() {
        return height;
    }

    public int getIconWidth() {
        return width;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
    	drawAnimalImage(g);
    }
    
    private void drawAnimalImage(Graphics g) {
    	Color oldColor = g.getColor();
        int textHeight = g.getFontMetrics().getHeight();
        int textWidth = g.getFontMetrics().charsWidth(caption.toCharArray(), 0,
                caption.length());
        /*
        System.out.print("No of Chars = " + caption.toCharArray().length + "[");
        for (char ch : caption.toCharArray())
                System.out.print(ch);
        System.out.println("]");
        System.out.println(width);
        System.out.println(height);
        */
    	g.setColor(Color.BLACK);
        g.drawRect(0, 0, width-1, height-1);
        int left = (width - textWidth)/2 + 1;
        int base = height - (height - textHeight)/2 - 3;
        g.drawString(caption, left, base);
        //g.drawRect(0, 0, width, height);
    	g.setColor(oldColor);
    }
}
