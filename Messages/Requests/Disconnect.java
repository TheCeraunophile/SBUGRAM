package Messages.Requests;

import java.io.Serializable;

public class Disconnect implements Serializable {

    private final String username;
    private final boolean update;

    public Disconnect(String username,boolean update) {
        this.username = username;
        this.update = update;
    }

    public String getDisconnected() {
        return username;
    }

    public boolean getUpdate(){
        return update;
    }
}
