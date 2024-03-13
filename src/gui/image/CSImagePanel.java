package gui.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CSImagePanel extends JPanel {
	List<String> pictureNames = new ArrayList<String>();
    int i=-1;
    File folder; 
	
	public CSImagePanel(String path) {
		this.setLayout(new BorderLayout());
		folder = new File(path);
		JLabel imageLabel = new JLabel();
		ImageIcon icon = new ImageIcon();
		imageLabel.setPreferredSize(new Dimension(360, 270));
		this.add(imageLabel);
		Image image;
    	Map<String, Image> namedImages = new TreeMap<String, Image>();
    	
	    try {
			for (File fileEntry : folder.listFiles()) {
		    	System.out.print(fileEntry.getName());
				image = ImageIO.read(fileEntry);
				pictureNames.add(fileEntry.getName());
				namedImages.put(fileEntry.getName(), image);
		    	System.out.println(image.getWidth(null) + "x" +
		    			image.getHeight(null));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    JButton jbtnNext = new JButton("Next");
	    JButton jbtnPrev = new JButton("Prev");
	    JButton jbtnReset = new JButton("Reset");
	    ActionListener btnControl = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String key; 
				if (evt.getActionCommand().equals("Next")) 
					key = getNextImage(1);
				else if (evt.getActionCommand().equals("Prev"))
					key = getNextImage(-1);
				else 
					key = pictureNames.get(0);
				Image originalPix = namedImages.get(key);
				icon.setImage(CSImageEntity.getImageToFitKeepRatio(originalPix,
						360, 270));	
				imageLabel.setIcon(icon);
				CSImagePanel.this.repaint();
			}
	    	
	    };
	    jbtnNext.addActionListener(btnControl);
	    jbtnPrev.addActionListener(btnControl);
	    jbtnReset.addActionListener(btnControl);
	    
	    JPanel btnPanel = new JPanel();
	    btnPanel.add(jbtnPrev);
	    btnPanel.add(jbtnNext);
	    btnPanel.add(jbtnReset);
	    this.add(btnPanel, BorderLayout.SOUTH);
		
	}
	

	private String getNextImage(int incr) {
		i += incr;
		if (i == pictureNames.size())
			i = 0;
		else if (i == -1)
			i = pictureNames.size()-1;
		
		return pictureNames.get(i);
	}
}

