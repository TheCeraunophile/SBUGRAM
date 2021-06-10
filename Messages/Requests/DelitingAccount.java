package Messages.Requests;

public class DelitingAccount {

    private final String username;
    private final String password;

    public DelitingAccount(String username, String password) {
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
