package cstest.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.image.CSImageEntity;

public class PhotoPathologyEditor extends JDialog {
		private Object[][] tableContents;
		private String[] columnNames;
		private JFrame parent;
		private String table;
		private Map<String, JTextField> fieldsByName;
		
		private JPanel contentPanel;
		private JPanel controlPanel;
		private JPanel textFieldPanel;
		private JPanel photoPanel;
		private JTextArea jtfPhotoDesc;
		private JLabel jlblPhoto;
		
		private int currRow;
		
		public PhotoPathologyEditor(JFrame parent) {
		    super(parent, "CS Photo Edit", true);
			setIconImage(new ImageIcon("images/monkeyicon.png").getImage());
		    fieldsByName = new HashMap<String, JTextField>();
			
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			
			jtfPhotoDesc = new JTextArea(3, 40);
			contentPanel.add(jtfPhotoDesc, BorderLayout.SOUTH);
			
			textFieldPanel = prepareFieldPanel();
			contentPanel.add(textFieldPanel, BorderLayout.NORTH);
			
			photoPanel = new JPanel();
			Dimension photoDim = new Dimension(320, 350);
			photoPanel.setMinimumSize(photoDim);
			photoPanel.setMaximumSize(photoDim);
			photoPanel.setPreferredSize(photoDim);
			jlblPhoto = new JLabel();
			//ImageIcon photoIcon = new ImageIcon("image/user_cu_04.png");
			//try {
				String photo = (String) tableContents[currRow][3];
				ImageIcon photoIcon = new ImageIcon(CSImageEntity.getImageToFitKeepRatio(
						new ImageIcon("csimg/" + photo).getImage(), 440, 330));	
				jlblPhoto.setIcon(photoIcon);
			//} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			photoPanel.add(jlblPhoto);
			contentPanel.add(photoPanel);
			
			controlPanel = new JPanel();
			String[] btnNames = {"Previous", "Edit", "Delete", 
					"Add", "Next"}; 
			JButton[] btns = new JButton[btnNames.length];
			for (int i=0; i<btnNames.length; i++) {
				btns[i] = new JButton(btnNames[i]);
				controlPanel.add(btns[i]);
			}
			
			/* hard code it for now to get the Load Photo nutton as btns[2]
			btns[2].addActionListener(
	    	        new ActionListener(){
	    	            public void actionPerformed(ActionEvent e) {
	    	            	SqlServerDbAccessor sqda = new SqlServerDbAccessor(
	    	                		"csdata.cd4sevot432y.us-east-1.rds.amazonaws.com",
	    	                		"csc312cloud", "c3s!c2Cld", "StudTest");
	    	            	JFileChooser fc = new JFileChooser();
	    	            	int returnVal = fc.showOpenDialog(EditPhotoDialog.this);

	    	                if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	                    File file = fc.getSelectedFile();
	    	                    ImageIcon oldPhoto = (ImageIcon)jlblPhoto.getIcon();
	    	                    ImageIcon newPhoto = new ImageIcon("image/"+file.getName());
	    	                    jlblPhoto.setIcon(newPhoto);
	    	                    //This is where a real application would open the file.
	    	                    System.out.println("Opening: " + file.getName() + ".");

	    	                    int dialogButton = JOptionPane.showConfirmDialog (null, 
	    	                    		"Click YES to commit, NO to cancel...",
	    	                    		"Set a Photo",JOptionPane.YES_NO_OPTION);
	    	                    if(dialogButton == JOptionPane.YES_OPTION) {
	    	                    	UpdatePhotoTest upt = new UpdatePhotoTest();
	    	                    	upt.updateImageInColumn("DL27Login", "Photo",
	    	                    			 "image/"+file.getName(), "WHERE ID = 6");
	    	                    }
	    	                    else {
	    	                    	jlblPhoto.setIcon(oldPhoto);
	    	                    }
	    	                }
	    	            }
	    	        }
	    	    );
			 */
			if (currRow == 0) 
				btns[0].setEnabled(false);
			else if (currRow == btns.length - 1)
				btns[btns.length - 1].setEnabled(false);
			
			btns[btns.length - 1].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					currRow++;
					setValuesForRow(currRow);
				}

				private void setValuesForRow(int currRow) {
					// TODO Auto-generated method stub
					
				}
				
			}); 
				
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			getContentPane().add(controlPanel, BorderLayout.SOUTH);
			
			pack();
		}

		private JPanel prepareFieldPanel() {
		    JPanel panel = new JPanel(new GridBagLayout());
		    GridBagConstraints cs = new GridBagConstraints();
		
		    cs.fill = GridBagConstraints.HORIZONTAL;
		    
		    /*
		    DbServerJTableModelAdaptor dao = new DbServerJTableModelAdaptor(sqda);
		    columnNames = dao.getColNamesForTable(table);
		    
		    tableContents = dao.getData(table);
		     */
		    columnNames = hardCodeColNames();
		    
		    tableContents = hardCodeContents();
		    currRow = 0;
		    
		    JLabel jLabel;
		    JTextField jTextField;
		    
		    Object data;
		    
		    for (int fieldSeq = 0; fieldSeq < columnNames.length; fieldSeq++) {
		    	jLabel = new JLabel(columnNames[fieldSeq] + ": ");
		    	cs.gridx = 0;
		    	cs.gridy = fieldSeq;
		    	cs.gridwidth = 1;
		    	panel.add(jLabel, cs);
		
		    	jTextField = new JTextField(30);
		    	
		    	data = tableContents[currRow][fieldSeq];
		    	
		    	jTextField.setText((data==null)?"":data.toString());
		    	jTextField.setEnabled(false);
		        cs.gridx = 1;
		        cs.gridy = fieldSeq;
		        cs.gridwidth = 2;
		        panel.add(jTextField, cs);
		        fieldsByName.put(columnNames[fieldSeq], jTextField);
		    }
		    
		    data = tableContents[currRow][5];
		    jtfPhotoDesc.setEnabled(false);
		    jtfPhotoDesc.setText((data==null)?"":data.toString());
			
			return panel;
		}

		private Object[][] hardCodeContents() {
			Object[][] contents = {{"1", "1", "skull front", "DSCN2170.jpg", "", "skull front"},
					{"1", "2", "skull top", "DSCN2168.jpg", "", "skull front"},
					{"1", "3", "skull left", "DSCN2169.jpg", "", "skull front"},
					{"1", "4", "skull right", "DSCN2170.jpg", "", "skull front"},
					{"1", "5", "skull bottom", "DSCN2171.jpg", "", "skull front"}		
			};
			return contents;
		}

		private String[] hardCodeColNames() {
			String[] colNames = {"SubjectId", "ImageId", "ImageNote", "ImageFile"};
			
			return colNames;
		}

		public static void main(String[] args) {
			PhotoPathologyEditor d = new PhotoPathologyEditor(null);
			d.setVisible(true);
		}
}
