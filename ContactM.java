

import java.io.Serializable;


public class ContactM implements Serializable {

    private static final long serialVersionUID = 1L;
    // Initialisation des variables en private
    private String firstname;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String zipcity;
    private String comment;
    private String photo;

    /**
     *
     * Default constructor to pass empty values if nothing is passed with it
     *
     */

    public ContactM(){
        firstname = "";
        name = "";
        email = "";
        phone = "";
        address = "";
        zipcity = "";
        comment = "";
        photo = "";
    }

    /**
     *
     * Overloading default constructor with values for the contact
     * @param firstname String firstname to add
     * @param name String name to add
     * @param email String email to add
     * @param phone String phone to add
     * @param address String address to add
     *
     */

    public ContactM(String firstname, String name, String email, String phone, String address, String zipcity, String photo) {
        super();
        this.firstname = firstname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.zipcity = zipcity;
        this.photo = photo;
        
    }

    // Getters & Setters

    /**
     *
     * Getter for name
     * @return value of name
     *
     */

    public String getName() {
        return name;
    }

    /**
     *
     * Setter for name
     * @return value of name to set / overwrite
     *
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Getter for firstname
     * @return value of firstname
     *
     */

    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * Setter for firstname
     * @return value of firstname to set / overwrite
     *
     */

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * Getter for email address
     * @return value of email address
     *
     */

    public String getEmail() {
        return email;
    }

    /**
     *
     * Setter for email address
     * @return value of email address to set / overwrite
     *
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * Getter for phone number
     * @return value of phone number
     *
     */

    public String getPhone() {
        return phone;
    }

    /**
     *
     * Setter for phone number
     * @return value of phone number to set / overwrite
     *
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * Getter for address
     * @return value of address
     *
     */

    public String getAddress() {
        return address;
    }

    /**
     *
     * Setter for address
     * @return value of address to set / overwrite
     *
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * Getter for zip code & city
     * @return value of zip code & city
     *
     */

    public String getZipcity() {
        return zipcity;
    }

    /**
     *
     * Setter for zip code & city
     * @return value of zip code & city to set / overwrite
     *
     */

    public void setZipcity(String zipcity) {
        this.zipcity = zipcity;
    }

    /**
     *
     * Getter for photo
     * @return value of photo
     *
     */

    public String getPhoto() {
        return photo;
    }

    /**
     *
     * Setter for photo
     * @return value of photo to set / overwrite
     *
     */

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}