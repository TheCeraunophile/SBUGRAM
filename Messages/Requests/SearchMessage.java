package Messages.Requests;

import java.io.Serializable;

public class SearchMessage implements Serializable {
    private String usernametest;

    public SearchMessage(String usernametest){
        this.usernametest=usernametest;
    }

    public String getUsernametest() {
        return usernametest;
    }

}
