package cstest.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.image.CSImageEntity;

public class ReduceImageSizeForImageBlockPanel {

	public static void main(String[] args) {
		String path = "csimg", newPath = "csvimg";
		String pictureName, newPictureName;
		Image image, newImage;
		File folder = new File(path);
		File newFolder = new File(newPath);
		newFolder.mkdir();
		File outputfile;
	    try {
			for (File fileEntry : folder.listFiles()) {
		    	System.out.print(fileEntry.getName());
				image = ImageIO.read(fileEntry);
				pictureName = fileEntry.getName();
		    	System.out.println(pictureName + ":" + 
		    			image.getWidth(null) + "x" +
		    			image.getHeight(null));
		    	newImage = CSImageEntity.getImageToFitKeepRatio(image,
						360, 270);
		    	newPictureName = newPath + "/" + pictureName + ".jpg";
		    	outputfile = new File(newPictureName);
		    	ImageIO.write((BufferedImage)newImage, "jpg", outputfile);
		    	System.out.println(newPictureName + ":" + 
		    			newImage.getWidth(null) + "x" +
		    			newImage.getHeight(null));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
