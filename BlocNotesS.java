import java.io.Serializable;

public class BlocNotesS implements Serializable{

    private static final long serialVersionUID = 1L;
    private String title;
    private String text;

    public BlocNotesS(String Title, String Text){
        this.title = Title;
        this.text = Text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString(){
        return title + " " + text ;
    }


}