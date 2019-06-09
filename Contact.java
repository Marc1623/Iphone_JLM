import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;


public class Contact extends JPanel implements ActionListener  {


    /**
     *
     * fichierContact is the file in which the list of contacts is saved
     *
     **/

	private static String fichierContact = "src\\Data\\contact.txt";

    // Reporting and initialization of variables

    Font fontL = new Font("SansSerif", Font.BOLD, 16);
    Font fontTF = new Font("SansSerif", Font.ITALIC, 14);
    Font fontB = new Font("SansSerif", Font.BOLD, 14);
    
    static String addPhotoT = "Ajouter Photo"; //link gallerie    
    static String addButtonT = "Nouveau contact";
    static String modifyButtonT = "Modifier";
    static String saveButtonT = "Sauvegarder";
    static String updateButtonT = "Enregister les modifications";
    static String cancelButtonT = "Retour";
    static String deleteButtonT = "Supprimer";
    static String backButtonT = "Retour";
    static String nameLabelT = "Nom";
    static String firstnameLabelT = "Prenom";
    static String phoneLabelT = "Numero de telephone";
    static String emailLabelT = "Email";
    static String addressLabelT = "Adresse";
    static String zipcityLabelT = "CP / Ville";
    static String errorDeleteT = "Erreur lors de la suppression.";
    static String errorModifyT = "Une erreur est survenue lors de la modification. Veuillez corriger.";
    static String errorWriteT = "Erreur lors de l'écriture dans le fichier.";
    static String errorUpdateT = "Erreur lors de la modification.";
    static String errorReadT = "Erreur de lecteur.";
    static String deletedField = "#valeurSupprimee";

    ContactV ContactV = new ContactV();

    // Labels & TextFields dimensions
    protected Dimension dimension = new Dimension(480, 40);

    // Panels
    protected JPanel scrollPanePanel = new JPanel(); // Contains the scrollPane
    protected JPanel buttonPanel = new JPanel(); // Contains all the buttons
    protected static JPanel labelTfPanel = new JPanel(); // Contains all the data about contact
    protected JPanel formPanel = new JPanel(); // Contains buttonPanel & labelTfPanel
    protected static JPanel fePanel = new JPanel();

    // Lists
    protected static JList contactL = new JList(); //List which contains the data

    // Buttons
    //protected static JButton addPhotoButton = new JButton(addPhotoT); //link gallerie
    protected static JButton addButton = new JButton(addButtonT);
    protected static JButton editButton = new JButton(modifyButtonT);
    private static JButton saveButton = new JButton(saveButtonT);
    protected static JButton updateButton = new JButton(updateButtonT);
    protected static JButton deleteButton = new JButton(deleteButtonT);
    private static JButton cancelButton = new JButton(cancelButtonT);
    private static JButton backButton = new JButton(backButtonT);
    
    
    // Link Gallerie
    
    protected static JTextField photoTF = new JTextField();
    protected static JLabel photoLabel = new JLabel();

    // TextFields
    private static JTextField
                                nameTF = new JTextField(),
                                firstnameTF = new JTextField(),
                                phoneTF = new JTextField(),
                                emailTF = new JTextField(),
                                addressTF = new JTextField(),
                                zipCityTF = new JTextField();

    // Labels
    private JLabel
                    nameLabel = new JLabel(nameLabelT),
                    firstnameLabel = new JLabel(firstnameLabelT),
                    phoneLabel = new JLabel(phoneLabelT),
                    emailLabel = new JLabel(emailLabelT),
                    addressLabel = new JLabel(addressLabelT),
                    zipCityLabel = new JLabel(zipcityLabelT);

    private static String[] contactList;
    private static String[] contactArray;
    protected static ContactM[] contactData;
    private static PhoneWindow parent;

    public Contact(PhoneWindow parent) {

    	this.parent = parent;
        JScrollPane scrollPane = new JScrollPane(contactL);

        // Setting up the panels used in the GUI
        scrollPanePanel.setLayout(new BorderLayout());
        formPanel.setLayout(new BorderLayout());

        // scrollPanePanel
        scrollPanePanel.add(scrollPane,BorderLayout.CENTER);

        // formPanel
        formPanel.add(buttonPanel,BorderLayout.NORTH);
        formPanel.add(labelTfPanel,BorderLayout.CENTER);
        formPanel.add(photoLabel,BorderLayout.SOUTH);
        
        // buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        contactL.addListSelectionListener(new LSListener());

        //labelTfPanel
        labelTfPanel.setLayout(new GridLayout(14, 1));
        labelTfPanel.add(nameLabel);
        labelTfPanel.add(nameTF);
        labelTfPanel.add(firstnameLabel);
        labelTfPanel.add(firstnameTF);
        labelTfPanel.add(phoneLabel);
        labelTfPanel.add(phoneTF);
        labelTfPanel.add(emailLabel);
        labelTfPanel.add(emailTF);
        labelTfPanel.add(addressLabel);
        labelTfPanel.add(addressTF);
        labelTfPanel.add(zipCityLabel);
        labelTfPanel.add(zipCityTF);

        // Adding both panels
        add(scrollPanePanel,BorderLayout.NORTH); // Contact list
        add(formPanel,BorderLayout.SOUTH); // Contact data

        // Hiding labelTfPanel
        labelTfPanel.setVisible(false);

        // Hiding the buttons
        editButton.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        backButton.setVisible(false);

        // Actions Listener
        addButton.addActionListener(new AddContact());
        editButton.addActionListener(new EditContact());
        saveButton.addActionListener(new ValidateNewContact());
        cancelButton.addActionListener(new CancelContact());
        updateButton.addActionListener(new ValidateExistingContact());
        deleteButton.addActionListener(new DeleteContact());
        backButton.addActionListener(new BackContact());

        // Link gallerie 
        photoLabel.add(photoTF);
        photoLabel.addMouseListener(new AddPhoto());
        
        this.setLayout(null);
        this.scrollPanePanel.setBounds(0, 100, 360, 350);
        this.formPanel.setBounds(0, 50, 360, 430);

        setStyles();
    }
    public void setStyles(){
        // FePanel
        fePanel.setBounds(0, 0, 310, 525);
        fePanel.setVisible(true);
        fePanel.setLayout(null);
        add(fePanel);

        addButton.setBounds(100, 35, 150, 35);
        addButton.setVisible(true);
        addButton.setLayout(null);
        fePanel.add(addButton);

        // nameLabel  & nameTF
        nameLabel.setFont(fontL);
        nameLabel.setPreferredSize(dimension);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(Color.LIGHT_GRAY);
        nameTF.setFont(fontTF);
        nameTF.setPreferredSize(dimension);

        // firstnameLabel & firstnameTF
        firstnameLabel.setFont(fontL);
        firstnameLabel.setPreferredSize(dimension);
        firstnameLabel.setOpaque(true);
        firstnameLabel.setBackground(Color.LIGHT_GRAY);
        firstnameTF.setFont(fontTF);
        firstnameTF.setPreferredSize(dimension);

        // phoneLabel & phoneTF
        phoneLabel.setFont(fontL);
        phoneLabel.setPreferredSize(dimension);
        phoneLabel.setOpaque(true);
        phoneLabel.setBackground(Color.LIGHT_GRAY);
        phoneTF.setFont(fontTF);
        phoneTF.setPreferredSize(dimension);

        //emailLabel & emailTF
        emailLabel.setFont(fontL);
        emailLabel.setPreferredSize(dimension);
        emailLabel.setOpaque(true);
        emailLabel.setBackground(Color.LIGHT_GRAY);
        emailTF.setFont(fontTF);
        emailTF.setPreferredSize(dimension);

        //addressLabel & addressTF
        addressLabel.setFont(fontL);
        addressLabel.setPreferredSize(dimension);
        addressLabel.setOpaque(true);
        addressLabel.setBackground(Color.LIGHT_GRAY);
        addressTF.setFont(fontTF);
        addressTF.setPreferredSize(dimension);

        //zipCityLabel & zipCityTF
        zipCityLabel.setFont(fontL);
        zipCityLabel.setPreferredSize(dimension);
        zipCityLabel.setOpaque(true);
        zipCityLabel.setBackground(Color.LIGHT_GRAY);
        zipCityTF.setFont(fontTF);
        zipCityTF.setPreferredSize(dimension);
        
        // Buttons font
        addButton.setFont(fontB);
        editButton.setFont(fontB);
        saveButton.setFont(fontB);
        cancelButton.setFont(fontB);
        updateButton.setFont(fontB);
        deleteButton.setFont(fontB);
        backButton.setFont(fontB);

    }

    public void setEditable(boolean val){
        nameTF.setEditable(val);
        firstnameTF.setEditable(val);
        phoneTF.setEditable(val);
        emailTF.setEditable(val);
        addressTF.setEditable(val);
        zipCityTF.setEditable(val);
    }
    public static void showDefault(){
        addButton.setVisible(true);
        photoLabel.setVisible(false);
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        updateButton.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        contactL.setEnabled(true);
    }
    public void setFieldsToNULL(){
        nameTF.setText(null);
        firstnameTF.setText(null);
        phoneTF.setText(null);
        emailTF.setText(null);
        addressTF.setText(null);
        zipCityTF.setText(null);
    }
    public void readContact() {
        String ligne;
        try {
            int buffLength = 0;
            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(fichierContact)));
            while ((buff.readLine()) != null) {
                buffLength++;
            }
            contactArray = new String[buffLength];
            buff.close();
        } catch (Exception e) {
            System.out.println(errorReadT);
            System.out.println(e.toString());
        }

        try {
            int buffLength = 0;
            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(fichierContact)));
            while ((ligne = buff.readLine()) != null) {
                contactArray[buffLength] = ligne;
                buffLength++;
            }
            buff.close();
            updateList();
            contactL.setEnabled(true);
        } catch (Exception e) {
            System.out.println(errorReadT);
            System.out.println(e.toString());
        }
    }
    public void writeContact(){
        try {
            try {
                PrintWriter pw = new PrintWriter(fichierContact);
                for (int i = 0; i< contactArray.length; i++){
                    if(contactArray[i]!= null){
                        if(!contactArray[i].contains("#delete")){
                            pw.println(contactArray[i]);
                        }
                    }
                }
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println(errorWriteT);
            System.out.println(e.toString());
        }
    }

    class LSListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent evt){
            int i = contactL.getSelectedIndex();
            labelTfPanel.setVisible(true); // Show contact form
            setEditable(false);
            editButton.setVisible(true);
            deleteButton.setVisible(true);
            backButton.setVisible(true);
            scrollPanePanel.setVisible(false);
            formPanel.setVisible(true);
            photoLabel.setVisible(true);
            addButton.setVisible(false);
            
            
            if(i != -1){
                nameTF.setText(contactData[i].getName());
                firstnameTF.setText(contactData[i].getFirstname());
                phoneTF.setText(contactData[i].getPhone());
                emailTF.setText(contactData[i].getEmail());
                addressTF.setText(contactData[i].getAddress());
                zipCityTF.setText(contactData[i].getZipcity());
                
                // save the contact item number  
                parent.gallerie.setLinkContact(i);
                if(contactData[i].getPhoto().equals("null")) {
                	ImageIcon icon = new ImageIcon(new ImageIcon ("src\\images\\anonyme.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                	photoLabel.setIcon(icon);
                	//photoLabel.setText("Ajouter Photo");
                	photoTF.setText("null");
                } else {
                	File f = new File("src\\photos\\" + contactData[i].getPhoto());
                	if (f.exists()) {
                	
                		ImageIcon icon = new ImageIcon(new ImageIcon ("src\\photos\\" + contactData[i].getPhoto()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    	photoLabel.setIcon(icon);
                    	photoLabel.setText("");
                    	photoTF.setText(contactData[i].getPhoto());
                	} else {
                		ImageIcon icon = new ImageIcon(new ImageIcon ("src\\images\\anonyme.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    	photoLabel.setIcon(icon);
                    	photoTF.setText("null");
                	}
                	
                }
                	
            }
        }
    }
    public void deleteConfirmation() {
        int reponse = JOptionPane.showConfirmDialog(this,
                "Suppression du contact. Êtes-vous sur de vouloir le supprimer ?",
                "Suppression",
                JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION){
            int numJList = contactL.getSelectedIndex();
            // replacing data by deletedField tags
            streamMod(deletedField, deletedField, deletedField, deletedField, deletedField, deletedField, deletedField, numJList);
        }
        showDefault();
    }

    public void updateList(){
        contactList = new String[contactArray.length];
        contactData = new ContactM[contactArray.length];

        String[] tempArray = new String[7];
        try {
            for (int i = 0; i< contactArray.length; i++){
                if(contactArray[i]!= null){
                    // Separating values to 7 values - used separator is - with a leading and ending space
                    tempArray = contactArray[i].split(" - ", 7);
                    // Creating contactData array contaning the object contactData with it's informations
                    contactData[i] = new ContactM(tempArray[0], tempArray[1], tempArray[2], tempArray[3], tempArray[4], tempArray[5], tempArray[6]);
                    // Generating contactList content
                    contactList[i] = tempArray[0] + " " + tempArray[1];
                    if(contactArray[i].contains(deletedField)){
                        contactList[i] = null;
                    }
                }
            }
            contactL.setListData(contactList);
            labelTfPanel.setVisible(false);
            backButton.setVisible(false);
            scrollPanePanel.setVisible(true);

        }catch (Exception e){
            System.out.println(errorUpdateT);
            System.out.println(e.toString());
        }
    }

    public boolean validPhone(String phone){
        boolean response;
        response = ContactV.validatePhone(phone);
        return response;
    }
    public boolean validEmail(String mail){
        boolean response;
        response = ContactV.validateEmail(mail);
        return response;
    }
    public void addToStream(){
        String temp [] = new String[contactArray.length + 1];

        // Creating temporary array and adding existing contact data
        for(int i = 0; i < contactArray.length; i++){
            temp[i] = contactArray[i];

        }
        // Appending the new contact to the last position of the temp array
        temp[contactArray.length] = firstnameTF.getText() + " - "
                + nameTF.getText() + " - " + emailTF.getText() + " - " + phoneTF.getText()
                + " - " + addressTF.getText() + " - " + zipCityTF.getText() +  " - " + photoTF.getText();

        // Redefining contactArray and putting temp data
        contactArray = new String [temp.length];
        contactArray = temp;

        writeContact();
        updateList();
        showDefault();
    }
    public void streamMod(String name, String firstname, String email, String phone, String address, String zipcity, String pathPhoto, int JListContact) {
        for(int i = 0; i< contactArray.length; i++){
            if(contactArray[i] != null){
                if(i == JListContact){
                    contactArray[i] = firstname + " - " + name + " - " + email + " - " + phone + " - " + address + " - " + zipcity + " - " + pathPhoto;
                }
            }
        }
        updateList();
        writeContact();
        contactL.setEnabled(true);
        showDefault();
    }
    class AddContact implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            contactL.setEnabled(false);
            setFieldsToNULL();
            scrollPanePanel.setVisible(false);
            formPanel.setVisible(true);
            labelTfPanel.setVisible(true);
            cancelButton.setVisible(true);
            saveButton.setVisible(true);
            addButton.setVisible(false);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            updateButton.setVisible(false);
            backButton.setVisible(false);
            setEditable(true);
        }
    }
    class EditContact implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int numJList = contactL.getSelectedIndex();
            if(numJList == -1){
                return;
            }
            updateButton.setVisible(true);
            cancelButton.setVisible(true);
            contactL.setEnabled(false);
            addButton.setVisible(false);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            saveButton.setVisible(false);
            setEditable(true);
            labelTfPanel.setVisible(true);
            backButton.setVisible(false);
        }
    }
    class ValidateNewContact implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(validEmail(emailTF.getText())){
                emailTF.setForeground(Color.BLACK);
                if(validPhone(phoneTF.getText())) {
                    phoneTF.setForeground(Color.BLACK);
                    addToStream();
                    setFieldsToNULL();
                    showDefault();
                } else {
                    phoneTF.setForeground(Color.RED);
                }
            } else {
                emailTF.setForeground(Color.RED);
            }
        }
    }
    class CancelContact implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                contactL.clearSelection(); // Clearing the contactLF
                labelTfPanel.setVisible(false); // Hiding labelTfPanel
                scrollPanePanel.setVisible(true);
                backButton.setVisible(false);
                showDefault(); // Set the buttons back to their initial state
                setFieldsToNULL(); // Deleting the content in the JTextFields
            } catch (Exception ex){
                System.out.println(errorDeleteT);
                System.out.println(ex.toString());
            }
        }
    }
    class BackContact implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //contactL.clearSelection(); // Clearing the contactLF
                scrollPanePanel.setVisible(true);
                labelTfPanel.setVisible(false); // Hiding labelTfPanel
                backButton.setVisible(false);
                showDefault(); // Set the buttons back to their initial state
                setFieldsToNULL(); // Deleting the content in the JTextFields
            } catch (Exception ex){
                System.out.println(errorDeleteT);
                System.out.println(ex.toString());
            }
        }
    }

    class ValidateExistingContact implements ActionListener{

        public void actionPerformed(ActionEvent e){
            try {
                int numJList = contactL.getSelectedIndex();                
                // Testing the contact form fields
                if(validEmail(emailTF.getText())){
                    emailTF.setForeground(Color.BLACK);
                    if(validPhone(phoneTF.getText())) {
                        phoneTF.setForeground(Color.BLACK);
                        // Getting form field values and writing data to the stream
                        streamMod(nameTF.getText(), firstnameTF.getText(),
                                emailTF.getText(), phoneTF.getText(), addressTF.getText(),
                                zipCityTF.getText(), photoTF.getText(), numJList);
                    } else {
                        phoneTF.setForeground(Color.RED);
                    }
                } else {
                    emailTF.setForeground(Color.RED);
                }
                showDefault();
            } catch (Exception f) {
                System.out.println(errorModifyT);
                System.out.println(f.toString());
            }
        }
    }
    class DeleteContact implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteConfirmation();
        }
    }
    
    
		
	class AddPhoto implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				parent.btnGalerie.doClick();
						
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
@Override
public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub

}
}