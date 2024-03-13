package gui.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

public class CSImageEntity {
	String catId;
	String type;
	Image picture;
	String fileName;
	static Set<String> typeIds = new HashSet<String>();
	static Set<String> distinctCatIds = new HashSet<String>();
	
	public CSImageEntity(String catId, String type, String fileName) {
		this.catId = catId;
		this.type = type;
		ImageIcon icon = new ImageIcon(fileName);
		picture = icon.getImage();
		getTypeIds().add(type);
		getDistinctCatIds().add(catId);
	}

	public static Image getImageToFitKeepRatio(Image picture, int width, int height) {
		CSImageEntity dummy = new CSImageEntity("dummy", "unknown", "unknown");
		dummy.picture = picture;
		return dummy.getImageToFit(width, height);
	}
	
	public Image getImageToFitKeepRatio(int width, int height) {
		  BufferedImage tThumbImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		  Graphics2D tGraphics2D = tThumbImage.createGraphics(); //create a graphics object to paint to
		  tGraphics2D.setBackground( Color.WHITE );
		  tGraphics2D.setPaint( Color.WHITE );
		  tGraphics2D.fillRect( 0, 0, width, height );
		  
		  double ratioW = 1.0 * getPicture().getWidth(null) / width;
		  double ratioH = 1.0 * getPicture().getHeight(null) / height;
		  System.out.print(ratioW + "-" +  ratioH);
		  int projW, projH;
		  if (ratioW > ratioH) {
			  projW = width;
			  projH = (int)(getPicture().getHeight(null) / ratioW);
			  System.out.println(" ==> " + projW + "*x" +  projH);
		  }
		  else {
			  projH = height;
			  projW = (int)(getPicture().getWidth(null) / ratioH);
			  System.out.println(" ==> " + projW + "x" +  projH +"*");
		  }
		  
		  int x0 = (width - projW)/2;
		  int y0 = (height - projH)/2;
		  tGraphics2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
		  tGraphics2D.drawImage( getPicture(), x0, y0, projW, projH, null ); //draw the image scaled
		return tThumbImage;		
	}

	public String getDimension() {
		// TODO Auto-generated method stub
		return getPicture().getWidth(null) + "x" + getPicture().getHeight(null);
	}
	
	public Image getImageToFit(int width, int height) {
		  BufferedImage tThumbImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		  Graphics2D tGraphics2D = tThumbImage.createGraphics(); //create a graphics object to paint to
		  tGraphics2D.setBackground( Color.WHITE );
		  tGraphics2D.setPaint( Color.WHITE );
		  tGraphics2D.fillRect( 0, 0, width, height );
		  tGraphics2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
		  tGraphics2D.drawImage( getPicture(), 0, 0, width, height, null ); //draw the image scaled
		return tThumbImage;		
	}

	public static Set<String> getDistinctCatIds() {
		return distinctCatIds;
	}

	public static Set<String> getTypeIds() {
		return typeIds;
	}

	public String getCatId() {
		return catId;
	}

	public Image getPicture() {
		return picture;
	}

	public String getType() {
		return type;
	}
}
