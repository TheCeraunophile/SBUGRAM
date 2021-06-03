package Messages.Requests;
import java.io.Serializable;

public class Connect implements  Serializable {

    private final String username;
    private final String password;

    public Connect(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
