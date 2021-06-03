package Messages.Requests;
import Server.Databace.User;
import java.io.Serializable;

public class DirectMessage implements Serializable {

    private final User sender;
    private final User risived;
    private final String textMessage;

    public DirectMessage(User sender, User risived, String textMessage) {
        this.sender = sender;
        this.risived = risived;
        this.textMessage = textMessage;
    }

    public User getSender() {
        return sender;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public User getRisived() {
        return risived;
    }
}
