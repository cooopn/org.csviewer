package cstest.image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import gui.image.CSImageEntity;

public class CSImageTest {
	String path = "C:/Research/NSF/Data/QianImage";
	
	public static void main(String[] args) {
		CSImageTest test = new CSImageTest();
		File directoryPath = new File(test.path);
		//List of all files and directories
		String contents[] = directoryPath.list();
		System.out.println("List of files and directories:");
		
		System.out.println(contents.length);
		String id, type, extension;
		int indexOf1stSpace, indexOfDot;
		ArrayList<CSImageEntity> list = new ArrayList<CSImageEntity>();
		for(int i=0; i<contents.length; i++) {
			//System.out.println(contents[i]);
			indexOf1stSpace = (contents[i]).trim().indexOf(' ');
			indexOfDot = contents[i].trim().indexOf('.');
			id = contents[i].substring(0, indexOf1stSpace);
			type = contents[i].substring(indexOf1stSpace + 1, 
					indexOfDot).trim();
			extension = contents[i].substring(indexOfDot + 1).
					trim().toLowerCase();
			CSImageEntity entity = new CSImageEntity(id, type, 
					test.path + "/" + contents[i]);
			list.add(entity);
			System.out.println(contents[i] + " " + entity.getDimension());
		}	
		List<String> list1 = new ArrayList<String>(CSImageEntity.getDistinctCatIds());
		Collections.sort(list1);
		System.out.println(list1);
		list1 = new ArrayList<String>(CSImageEntity.getTypeIds());
		Collections.sort(list1);
		System.out.println(list1);

		boolean toShowMore = true;
		int choice = 0;
		int option;
		String info;
		String[] options = {"Prev", "Next", "Pick", "Quit"};
		CSImageEntity entity;
		while (toShowMore) {
			entity = list.get(choice);
			info = "Catalog No: " + entity.getCatId() + "\n" +
				    "Image Type: " + entity.getType() + "\n" +
				    "  (" + entity.getPicture().getWidth(null) + "x" +
				    entity.getPicture().getHeight(null) + ")";
			System.out.print("For picture " + entity.getCatId() + ": ");
			option = JOptionPane.showOptionDialog(null, info, 
					"CS Rhesus Specimens", JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					//new ImageIcon(entity.getImageToFit(400, 300)),
					new ImageIcon(entity.getImageToFitKeepRatio(400, 300)),
					options, "Next");
			switch (option) {
			default:
				toShowMore = false;
				break;
			case 0:
				choice--;
				if (choice < 0)
					choice = list1.size() -1;
				break;
			case 1: 
				choice++;
				break;
			case 2:
				choice = list1.size() -1; //= JOptionPane.showInputDialog("Type in catalog #");
				break;
			}	
		}
	}

}

