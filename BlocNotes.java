import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class BlocNotes extends JPanel{
    private static final long serialVersionUID = 1L;

    protected CardLayout cardLayout = new CardLayout ();
    protected JPanel cardsPanel = new JPanel(cardLayout);

    private static String fichierNotes = "src\\Data\\notes.txt";

    Font fontL = new Font("SansSerif", Font.BOLD, 16);
    Font fontTF = new Font("SansSerif", Font.ITALIC, 14);
    Font fontB = new Font("SansSerif", Font.BOLD, 14);

    ////JButtons/////////////////////////////////////////
    protected JButton newNote = new JButton("Nouvelle note");
    private JButton back = new JButton("Retour");
    private JButton back2 = new JButton("Retour");
    private JButton save = new JButton("Sauvegarder");
    private JButton saveNew = new JButton("Sauvegarder");
    private JButton cancel = new JButton("Annuler");
    protected JButton delete = new JButton("Effacer");
    protected JButton edit = new JButton("Modifier");

    ////JTextField and Area////////////////////////////////////////////

    protected JTextField oldTitle = new JTextField("Écrivez votre titre ici");
    protected JTextField newTitle = new JTextField("Écrivez votre titre ici");
    protected JTextArea oldText = new JTextArea();
    protected JTextArea newText = new JTextArea();

    ////JPanels////////////////////////////////////////////

    protected JPanel contentPanel = new JPanel();
    protected JPanel homePagePanel = new JPanel();
    protected JPanel newPanel = new JPanel();
    protected JPanel oldPanel = new JPanel();
    protected JPanel emptyPanel = new JPanel();

    ////Other////////////////////////////////////////////

    protected static JList noteList = new JList();
    protected static BlocNotesS[] noteData;

    ////String arrays////////////////////////////////////////////

    protected String[] listContent = {"Empty","New","Old"};
    protected String[] titleList;
    protected String[] chain;
    protected String[] splitedNote = new String[1];

    public BlocNotes(){
        Border emptyBorder = BorderFactory.createEmptyBorder();

        StartingPanel startingPanel = new StartingPanel();
        NewNotePanel newNotePanel = new NewNotePanel();
        OldNotePanel oldNotePanel = new OldNotePanel();
        ContentPanel ContentPanel = new ContentPanel();

        this.add(homePagePanel);
        this.add(contentPanel);

        this.setLayout(null);
        this.homePagePanel.setBounds(0, 100, 360, 350);
        this.contentPanel.setBounds(0, 50, 360, 430);

        setStyle();
    }

    /**
     * Setting styles for the components
     */

    private void setStyle(){

        //Formatting List
        noteList.setFont(fontL);

        //Formatting TextFields
        newTitle.setPreferredSize(new Dimension (250,30));
        newTitle.setFont(fontTF);
        oldTitle.setPreferredSize(new Dimension(250,30));
        oldTitle.setFont(fontTF);

        //Formatting TextArea
        newText.setFont(fontTF);
        newText.setLineWrap(true);
        oldText.setFont(fontTF);
        oldText.setLineWrap(true);

        //Formatting Buttons
        newNote.setFont(fontB);
        back.setFont(fontB);
        back2.setFont(fontB);
        save.setFont(fontB);
        saveNew.setFont(fontB);
        cancel.setFont(fontB);
        delete.setFont(fontB);
        edit.setFont(fontB);

    }

    private void showDefaultButtons(){
        newNote.setVisible(false);
        noteList.setEnabled(false);
        edit.setVisible(false);
        delete.setVisible(false);
        back.setVisible(false);
        saveNew.setVisible(true);
        cancel.setVisible(true);
    }

    private void showNewButtons(){
        saveNew.setVisible(false);
        cancel.setVisible(false);
        edit.setVisible(true);
        delete.setVisible(true);
        back.setVisible(true);
        newNote.setVisible(true);
        noteList.setEnabled(true);
    }


    /**
     * Read the file in order to create an array from data contained in the file
     */
    public void readingFile(){
        try{
            int buffLength = 0;
            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(fichierNotes)));
            while ((buff.readLine())!= null ){
                buffLength++;
            }
            chain = new String[buffLength];
            buff.close();
        }catch (Exception e){
            System.out.println("Reading error");
            System.out.println(e.toString());
        }

        try{
            String line;
            String enterLine;
            int num = 0;

            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(fichierNotes)));

            while ((line = buff.readLine())!= null ){
                if(line.contains("#Enter")){
                    enterLine = line;
                    enterLine = enterLine.replaceAll("#Enter", "\r\n");
                    chain[num] = enterLine;
                }
                else{
                    chain[num] = line ;
                }
                num++;
            }
            buff.close();
            updateList();
        }
        catch (Exception e){
            System.out.println("Reading error");
            System.out.println(e.toString());
        }
    }

    /**
     * Write the content of the array in the file
     */

    public void writingFile(){
        String text = "";
        try {
            PrintWriter outputFile = new PrintWriter(new BufferedWriter (new FileWriter (fichierNotes, false)));
            for (int i = 0; i<chain.length; i++){
                if(chain[i]!= null){
                    if(!chain[i].contains("#delete")){
                        if(chain[i].contains("\n")){
                            text = chain[i];
                            text = text.replaceAll("[\r\n]+"," #Enter ");
                            chain[i] = text;
                        }
                        outputFile.println(chain[i]);
                    }
                }
            }
            outputFile.close();
        }catch (Exception e){
            System.out.println("Writing error");
            System.out.println(e.toString());
        }
    }

    /**
     * Updates the JList
     */

    protected void updateList(){
        titleList = new String[chain.length];
        noteData = new BlocNotesS[chain.length];
        for(int i = 0; i<chain.length; i++){
            if(chain[i]!=null){
                if(!chain[i].contains("#delete")){
                    splitedNote = chain[i].split(" - ", 2);
                    noteData[i] = new BlocNotesS(splitedNote[0], splitedNote[1]);
                    titleList[i] = splitedNote[0];
                }
            }
        }
        noteList.setListData(titleList);
        contentPanel.setVisible(false);
        homePagePanel.setVisible(true);
    }

    private void addInChain(){
        String temp[] = new String[chain.length+1];
        for(int i = 0; i<chain.length;i++){
            temp[i] = chain[i];
        }

        temp[chain.length] = newTitle.getText() + " - " + newText.getText();
        chain = new String[temp.length];
        chain = temp;

        writingFile();
        updateList();
    }

    private void EditChain(String title, String text, int num) {
        for(int i = 0; i<chain.length; i++){
            if(chain[i] != null ){
                if(i==num){
                    chain[i] = title + " - " + text ;
                }
            }
        }
        updateList();
    }

    class NewAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(contentPanel, listContent[1]);
            showDefaultButtons();
            homePagePanel.setVisible(false);
            newNote.setVisible(false);
            contentPanel.setVisible(true);
        }
    }

    class SaveAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(newTitle.getText().length()==0){

            }else{
                addInChain();
                newTitle.setText(null);
                newText.setText(null);
                noteList.setEnabled(true);
                newNote.setVisible(true);
                cardLayout.show(contentPanel, listContent[0]);
                showNewButtons();
            }
        }
    }

    class EditAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            oldTitle.setEditable(true);
            oldText.setEditable(true);
            showDefaultButtons();
        }
    }

    class EditSaveAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource().equals(cancel)){
                    oldTitle.setText(noteData[noteList.getSelectedIndex()].getTitle());
                    oldText.setText(noteData[noteList.getSelectedIndex()].getText());
                }else{
                    EditChain(oldText.getText(),oldText.getText(),noteList.getSelectedIndex());
                }
                oldTitle.setEditable(false);
                oldText.setEditable(false);
                showNewButtons();
            }catch(Exception ex){
                System.out.println("Edit error");
                System.out.println(ex.toString());
            }
            updateList();
        }
    }

    class DeleteAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            EditChain("#delete", "#delete", noteList.getSelectedIndex());
            newNote.setVisible(true);
            cardLayout.show(contentPanel, listContent[0]);
        }
    }

    class CancelAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            showNewButtons();
            newTitle.setText(null);
            newText.setText(null);
            cardLayout.show(contentPanel, listContent[0]);
            contentPanel.setVisible(false);
            homePagePanel.setVisible(true);
        }
    }

    class ListListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            oldTitle.setText(noteData[noteList.getSelectedIndex()].getTitle());
            oldText.setText(noteData[noteList.getSelectedIndex()].getText());
            cardLayout.show(contentPanel, listContent[2]);
            contentPanel.setVisible(true);
            homePagePanel.setVisible(false);
        }
    }

    class ContentPanel{
        public ContentPanel(){
            contentPanel.setLayout(cardLayout);
            contentPanel.add(emptyPanel, listContent[0]);
            contentPanel.add(newPanel, listContent[1]);
            contentPanel.add(oldPanel, listContent[2]);
        }
    }

    class StartingPanel{
        public StartingPanel(){
            Border emptyBorder = BorderFactory.createEmptyBorder();
            JScrollPane scrollPane = new JScrollPane(noteList);
            JPanel ButtonPanel = new JPanel(new FlowLayout());
            scrollPane.setBorder(emptyBorder);
            ButtonPanel.add(newNote);

            noteList.addMouseListener(new ListListener());
            newNote.addActionListener(new NewAction());

            homePagePanel.setLayout(new BorderLayout());
            homePagePanel.add(scrollPane);
            homePagePanel.add(ButtonPanel, BorderLayout.SOUTH);

        }
    }

    class NewNotePanel{
        public NewNotePanel(){
            save.addActionListener(new SaveAction());
            back2.addActionListener(new CancelAction());
            newPanel.add(newTitle, BorderLayout.NORTH);
            newPanel.add(newText, BorderLayout.NORTH);
            newPanel.add(save, BorderLayout.NORTH);
            newPanel.add(back2, BorderLayout.NORTH);
        }
    }

    class OldNotePanel{
        public OldNotePanel(){
            oldTitle.setEditable(false);
            oldText.setEditable(false);
            saveNew.setVisible(false);
            cancel.setVisible(false);

            back.addActionListener(new CancelAction());
            edit.addActionListener(new EditAction());
            saveNew.addActionListener(new EditSaveAction());
            delete.addActionListener(new DeleteAction());
            cancel.addActionListener(new EditSaveAction());

            JScrollPane scrollPane2 = new JScrollPane(oldPanel);
            oldPanel.add(oldTitle, BorderLayout.NORTH);
            oldPanel.add(oldText, BorderLayout.CENTER);
            oldPanel.add(edit);
            oldPanel.add(delete);
            oldPanel.add(back);
            oldPanel.add(saveNew);
            oldPanel.add(cancel);

            //contentPanel.add(scrollPane2);


        }
    }
}