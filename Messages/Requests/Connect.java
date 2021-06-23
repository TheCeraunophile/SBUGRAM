package Messages.Requests;
import java.io.Serializable;

public class Connect implements  Serializable {

    private final String username;
    private final String password;
    private final boolean update;
    public Connect(String username, String password,boolean update) {
        this.username = username;
        this.password = password;
        this.update = update;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean getUpdate(){
        return this.update;
    }
}
