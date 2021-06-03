package Messages.Requests;
import java.io.Serializable;
import java.util.Date;

public class CreatingAccount implements Serializable {

    private final String username;
    private final String password;
    private final Date birthdayDate;
    private final String bio;

    public CreatingAccount(String username, String password, Date birthdayDate, String bio) {
        this.username = username;
        this.password = password;
        this.birthdayDate = birthdayDate;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public String getBio() {
        return bio;
    }
}
