package cstest.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RenameImageFiles {

	public static void main(String[] args) {
		String path = "csimg";
	    File folder = new File(path);
	    File newFile;
	    String oldFileName, newFileName;;
    	boolean success = false;
	    for (File oldFile : folder.listFiles()) {
			oldFileName = oldFile.getName();
			if (oldFileName.startsWith("Copy")) {
				newFileName = oldFileName.substring(8, 16) + ".JPG";
				newFile = new File(newFileName);
				success = oldFile.renameTo(newFile);
			}
			if (!success)
				System.out.println(oldFileName + " failed");
		}

	}

}
