
	
import java.awt.*;

import javafx.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

/**
 * 
 * @author marc vial
 *
 */

public class PhoneWindow extends JFrame implements ActionListener, MouseListener {
	
		
	
	private static final long serialVersionUID = 1L;
		protected CardLayout cardLayout = new CardLayout ();
		protected JPanel cardsPanel = new JPanel(cardLayout);		
		protected JButton btnGalerie = new JButton();
		protected JButton btnCalculette = new JButton();
		protected JButton btnCentral = new JButton();
		protected JButton btnContact = new JButton();
		protected JButton btnNote = new JButton();

		
		protected Calculator calculator = new Calculator ();
		protected Gallerie gallerie = new Gallerie (this); //link between Gallerie and PhoneWindow
		protected Contact contact = new Contact(this); //link between Contact and PhoneWindow
		protected BlocNotes blocNotes = new BlocNotes();
		
	    protected JFrame frame = new JFrame();
		protected JPanel panel = new JPanel();
		
		public PhoneWindow () {
			
			gallerie.setLinkContact(-1);
			frame.setUndecorated(true);
			//frame.setShape(new RoundRectangle2D.Double(10, 10, 300, 600, 50, 50));
			frame.setShape(new RoundRectangle2D.Double(10, 10, 400, 600, 50, 50));
			frame.setSize(480, 800);

			Container c = frame.getContentPane();
			c.setBackground(Color.BLACK);
			frame.setLayout(null);
			frame.setVisible(true);
			
			
			cardsPanel.setSize(280,500);
			cardsPanel.setBackground(Color.WHITE);
			cardsPanel.setBounds(30, 50, 360, 480);
			
			//frame.add(panel);
			frame.add(cardsPanel);
			//panel.setLayout(cardLayout);
			//panel.add(calculator, listcontent[0]);

			panel.setLayout(null);
			panel.setSize(360,480);
			panel.setBackground(Color.WHITE);
			//panel.setBounds(30, 50, 360, 480);
			
			
			
			//JButton btnContact 
			
			ImageIcon icon = new ImageIcon("src/images/contact.jpg");
			Image im = icon.getImage();
			Image im2 = im.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			btnContact.setFocusPainted(false);
			btnContact.setBorderPainted(false);
			btnContact.setContentAreaFilled(false);
			btnContact.setIcon(new ImageIcon(im2));
			
			
			btnContact.setBounds(20, 400, 60 , 60);
			btnContact.addActionListener(this);
			panel.add(btnContact);
			
			
			//JButton btnGalerie
			
			
			ImageIcon iconG = new ImageIcon ("src/images/galerie.png");
			Image imG = iconG.getImage();
			Image imG2 = imG.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			btnGalerie.setFocusPainted(false);
			btnGalerie.setBorderPainted(false);
			btnGalerie.setContentAreaFilled(false);
			btnGalerie.setIcon(new ImageIcon(imG2));
			
			btnGalerie.setBounds(180, 400, 60, 60);
			btnGalerie.addActionListener(this);
			panel.add(btnGalerie);
			
			
			//JButton btnNote
			
			ImageIcon iconM = new ImageIcon ("src/images/note.jpg");
			Image imM = iconM.getImage();
			Image imM2 = imM.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			btnNote.setFocusPainted(false);
			btnNote.setBorderPainted(false);
			btnNote.setContentAreaFilled(false);
			btnNote.setIcon(new ImageIcon(imM2));

			btnNote.setBounds(100, 400, 60, 60);
			btnNote.addActionListener(this);
			panel.add(btnNote);
			
			
			//JButton btnCalculette
			
			
			ImageIcon iconC = new ImageIcon ("src/images/Calculator.png");
			Image imC = iconC.getImage();
			Image imC2 = imC.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			btnCalculette.setFocusPainted(false);
			btnCalculette.setBorderPainted(false);
			btnCalculette.setContentAreaFilled(false);
			btnCalculette.setIcon(new ImageIcon(imC2));
			
			btnCalculette.setBounds(260, 400, 60, 60);
			btnCalculette.addActionListener(this);
			panel.add(btnCalculette);

			panel.setBackground(Color.WHITE);
			
			//Jbutton btnCentral			
			ImageIcon iconCe = new ImageIcon ("src/images/Central.png");
			Image imCe = iconCe.getImage();
			Image imCe2 = imCe.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			btnCentral.setFocusPainted(false);
			btnCentral.setBorderPainted(false);
			btnCentral.setContentAreaFilled(false);
			btnCentral.setIcon(new ImageIcon(imCe2));
			
			btnCentral.setBounds(180 ,540 , 60, 60);
			btnCentral.addActionListener(this);
			btnCentral.addMouseListener(this);

			frame.add(btnCentral);	    
					
			cardsPanel.add(calculator,"calcul");
			cardsPanel.add(gallerie,"gallerie");
			cardsPanel.add(panel,"ini");
			cardsPanel.add(contact,"contact");
			cardsPanel.add(blocNotes,"blocNotes");
			
			cardLayout.show(cardsPanel,"ini");
			frame.repaint();
					
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub					
			
			if (e.getSource()==btnCentral) {
				cardLayout.show(cardsPanel, "ini");
				gallerie.setLinkContact(-1);
			}
			
			if (e.getSource()==btnGalerie) {
				cardLayout.show(cardsPanel, "gallerie");
			}
			
			if (e.getSource()==btnCalculette) {
				cardLayout.show(cardsPanel, "calcul");
			}
			
			if (e.getSource()==btnContact) {
				cardLayout.show(cardsPanel, "contact");
			}
			if (e.getSource()== btnNote) {
				cardLayout.show(cardsPanel, "blocNotes");
				}
			
			blocNotes.readingFile();
			contact.readContact();
			frame.repaint();
			
		}
		
		class WindowListener extends WindowAdapter {


			public void windowClosing(WindowEvent e){
				blocNotes.writingFile();
				contact.writeContact();
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			if (arg0.getClickCount()==2) {
				//gestion du double clique
				frame.dispose();
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


