package Messages.Requests;
import Server.Databace.User;

import java.io.Serializable;

public class PostMessage implements Serializable {

    private final User sender;
    private final String text;

    public PostMessage(User sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }
}
