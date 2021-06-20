package Messages.Requests;

import java.io.Serializable;

public class CompeerMessage implements Serializable {
    private final User sender;
    private final CompeerType compeerType;
    private final User receiver ;
    public CompeerMessage(User sender,User receiver,CompeerType compeerType){
        this.receiver=receiver;
        this.sender =sender;
        this.compeerType=compeerType;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public CompeerType getCompeerType() {
        return compeerType;
    }

}
