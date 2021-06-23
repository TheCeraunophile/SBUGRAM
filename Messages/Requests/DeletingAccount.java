package Messages.Requests;

import java.io.Serializable;

public class DeletingAccount implements Serializable {

    private final String username;
    private final String password;

    public DeletingAccount(String username, String password) {
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
