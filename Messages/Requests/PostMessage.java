package Messages.Requests;

import java.io.Serializable;

public class PostMessage implements Serializable {

    private final String usernameSender;
    private final String text;

    public PostMessage(String usernameSender, String text) {
        this.usernameSender = usernameSender;
        this.text = text;
    }

    public String getUsernameSender() {
        return usernameSender;
    }

    public String getText() {
        return text;
    }
}
