import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardCopyOption.*;

import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class gallerie show images in the spécific directory src/images their is the possibility to add and delete images
 *  
 * @author marc Vial
 *
 */


public class Gallerie extends JPanel implements ActionListener, MouseListener{
	
	// to switch beetween several iphone screen
	protected CardLayout cardLayout = new CardLayout ();
	
	
    // Buttons
	protected JButton btnClose = new JButton("< Gallerie");
	protected JButton btnNext = new JButton(">");
	protected JButton btnPrev = new JButton("<");
	protected JButton btnSupp = new JButton ("-");
	protected JButton btnAjoute = new JButton ("+");
	
	
	// Panels
	protected JPanel cardsPanel = new JPanel(cardLayout);
	protected JPanel panC = new JPanel();
	protected JPanel panImg = new JPanel();
	protected JPanel panBtnBas = new JPanel();
	protected JPanel panDialog = new JPanel();
	
	
	// Collect images in the directory
	protected File[] files;
	
	
	// Add new images
	protected JFileChooser chooser = new JFileChooser();
	
	
	protected int cptPhotos=0;
	
	
	// identify index of contact
	protected int linkContact;
	
	
	// Allows to have a link beetween Gallerie and Contact
    private static PhoneWindow parent;
    
    
    /**
     * Setter of LinkContact
     * @param index
     */
    public void setLinkContact (int index) {
		linkContact = index;
	}
    
    
	/**
	 *  Getter of LinkContact
	 * @return LinkContact
	 */
	public int getLinkContact () {
		return linkContact;
	}
	
	
	/**
	 * Constructor of Gallerie
	 * @param parent
	 */
	public Gallerie(PhoneWindow parent) {
		
		this.parent = parent;
		this.setSize(360,480);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		
		
		btnClose.addActionListener(this);
		btnClose.setVisible(false);
		
		
		this.setLayout(new BorderLayout());
		this.add(btnClose,BorderLayout.NORTH);
		
		panC.setLayout(new GridLayout(5, 4, 3, 3));
		cardsPanel.add(panC,"gal");
		cardsPanel.add(panImg,"img");
		cardsPanel.add(panDialog,"dia");		
       
		this.add(cardsPanel,BorderLayout.CENTER);
		
		chooser.addActionListener(this);
		panDialog.add(chooser);
		
		
		btnNext.addActionListener(this);
		btnPrev.addActionListener(this);
		btnAjoute.addActionListener(this);
		btnSupp.addActionListener(this);

		btnPrev.setVisible(false);
				
		panBtnBas.setLayout(new GridLayout(1,0,3,10));
		panBtnBas.add(btnPrev);
		panBtnBas.add(btnAjoute);
		panBtnBas.add(btnSupp);
		panBtnBas.add(btnNext);
		this.add(panBtnBas,BorderLayout.SOUTH);
		
		
		btnSupp.setEnabled(false); 
		
		//List JPG file in the photo directory
        files = new File("src\\photos").listFiles(new FilenameFilter() {
			public boolean accept(File file, String fileName) {
				return fileName.endsWith(".jpg");
			}
		});
        
        // View the first 20 photos on the gallerie screen
        while (cptPhotos < 20 && cptPhotos < files.length) {
        
            try {
                //System.out.println(cptPhotos + "; " + files[cptPhotos]);
            	JLabel lbl = new JLabel(new ImageIcon(ImageIO.read(files[cptPhotos]).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                JTextField pathImg = new JTextField(files[cptPhotos].getName());
                lbl.add(pathImg);
                lbl.setName("lbl" + cptPhotos);
                lbl.addMouseListener(this);
                panC.add(lbl);                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            cptPhotos++;
        }
    }
		
	
		
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		// return to the main screen of Gallerie
		if(e.getSource()==btnClose) {
			cardLayout.show(cardsPanel, "gal");
			btnClose.setVisible(false);
			panBtnBas.setVisible(true);
			btnPrev.setVisible(true);
			btnNext.setVisible(true);
			btnSupp.setEnabled(false); 
			btnAjoute.setVisible(true);
		}
		
		// view the next 20 images
		if(e.getSource()==btnNext) {
			btnPrev.setVisible(true);
			//charger les prochaines photos
			panC.removeAll();
			int cpt = cptPhotos + 20;
			while (cptPhotos < cpt && cptPhotos < files.length) {
		        
		            try {
		                //System.out.println(cptPhotos + "; " + files[cptPhotos]);
		                JLabel lbl = new JLabel(new ImageIcon(ImageIO.read(files[cptPhotos]).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		                lbl.setName("lbl" + cptPhotos);
		                lbl.addMouseListener(this);
		                panC.add(lbl);                
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		            cptPhotos++;
		        }	
			panC.repaint();
			this.revalidate();
			cardLayout.show(cardsPanel, "gal");
			if (cptPhotos==files.length) {
				btnNext.setVisible(false);
			}
			
		}
		
		// view the 20 previous images
		if (e.getSource()==btnPrev) {
			
			//System.out.println(cptPhotos);
			btnNext.setVisible(true);
			//charger les photos précédentes
			panC.removeAll();
			if(cptPhotos % 20 ==0)
				cptPhotos = cptPhotos - 40;
			else
				cptPhotos = cptPhotos - (cptPhotos % 20 ) - 20;
			//System.out.println(cptPhotos);
			int cpt = cptPhotos + 20;
			while (cptPhotos < cpt && cptPhotos < files.length) {
		       
		            try {
		                //System.out.println(cptPhotos + "; " + files[cptPhotos]);
		            	JLabel lbl = new JLabel(new ImageIcon(ImageIO.read(files[cptPhotos]).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		                lbl.setName("lbl" + cptPhotos);
		                lbl.addMouseListener(this);
		                panC.add(lbl);                
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		            cptPhotos++;
		        }		
			panC.repaint();
			this.revalidate();
			cardLayout.show(cardsPanel, "gal");
			if (cptPhotos<21) {
				btnPrev.setVisible(false);
			}
			
		}
		
		// Load the fileChooser the be able to add new JPG images
		if (e.getSource()==btnAjoute)  {
			
			cardLayout.show(cardsPanel, "dia");
			chooser.setVisible(true);
			chooser.setSize(cardsPanel.getHeight(),cardsPanel.getWidth());
			chooser.setPreferredSize(cardsPanel.getSize());
			panBtnBas.setVisible(false);
			
			// Select the filter to add only JPG images
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG Images", "jpg");
		    chooser.setFileFilter(filter);
            
		}
		
		
		if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
			
			//add the new image from the PC to the directory src/images if the image already exist it will be replaced 
	    	File fichierSelect = chooser.getSelectedFile();
			File fichierDest = new File("src\\photos\\" + fichierSelect.getName());
				
	    	try {
				Files.copy(fichierSelect.toPath(), fichierDest.toPath(),StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    	//List JPG file in the photo directory
	        files = new File("src\\photos").listFiles(new FilenameFilter() {
				public boolean accept(File file, String fileName) {
					return fileName.endsWith(".jpg");
				}
			});
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       
	       btnClose.doClick();
	    }
			
			
		// if you choose cancel it will simulate the return of main Gallerie
		if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
			
			btnClose.doClick();		
		}
		
		
		// delete the image and return to main screen of Gallerie, only possible if you have selected an image
		if (e.getSource()== btnSupp){
			
			//System.out.println(panImg.getComponent(0).getName());
			String nameCpn = panImg.getComponent(0).getName();
			int posFile = Integer.decode(nameCpn.substring(3));
			
			files[posFile].getAbsoluteFile().delete();
			
			btnClose.doClick();			
		}
		
		
		
		
	}





	@Override
	public void mouseClicked(MouseEvent arg0) {
		int link = parent.gallerie.getLinkContact();
		//identify the selected image
		String nameCpn = arg0.getComponent().getName();
		int posFile = Integer.decode(nameCpn.substring(3));
		
		String nFich = files[posFile].getName();
		// if link is more than 0, we save the image on the correct contact
		if (link>=0) {
		   
			ImageIcon icon = new ImageIcon(new ImageIcon ("src\\photos\\" + nFich).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			
			parent.contact.contactL.setSelectedIndex(parent.gallerie.getLinkContact());
			parent.contact.photoLabel.setIcon(icon);			
			parent.contact.photoTF.setText(nFich);
			parent.contact.updateButton.doClick();
			
			parent.btnContact.doClick();
		// else, we view the image in a bigger size
		} else {
			
			//System.out.println("clique sur images");
			ImageIcon icon = new ImageIcon(new ImageIcon ("src\\photos\\" + nFich).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
			JLabel lblImg = ((JLabel)arg0.getComponent());
			lblImg.setIcon(icon);
			
			panImg.removeAll();
			panImg.add(lblImg);
			panImg.repaint();
			
			cardLayout.show(cardsPanel, "img");
			
			btnClose.setVisible(true);
			panBtnBas.setVisible(true);
			btnNext.setVisible(false);
			btnPrev.setVisible(false);
			
			btnSupp.setEnabled(true); 
			
		}
	}





	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}





}
