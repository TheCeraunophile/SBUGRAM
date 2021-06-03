package Messages.Requests;

import java.io.Serializable;

public class Refresh implements Serializable {
    private String username;

    public Refresh(String username){
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
