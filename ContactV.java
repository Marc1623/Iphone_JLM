

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContactV {

    public ContactV() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * Method used to validate email address
     * Checking if there is text before and after the @ sign
     * Between two and four chars after the separating sign .
     * @param email
     * @return boolean
     *
     */

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher matched = pattern.matcher(email.toUpperCase());
        return matched.matches();
    }

    /**
     *
     * Method used to validate phone number
     * Checking if there are 10 chars
     * @param phone
     * @return boolean
     *
     */

    public boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matched = pattern.matcher(phone);
        return matched.matches();
    }

}