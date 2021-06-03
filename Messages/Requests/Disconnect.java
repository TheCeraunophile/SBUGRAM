package Messages.Requests;

import java.io.Serializable;

public class Disconnect implements Serializable {

    private final String username;

    public Disconnect(String username) {
        this.username = username;
    }

    public String getDisconnected() {
        return username;
    }
}
