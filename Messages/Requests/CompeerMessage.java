package Messages.Requests;

import java.io.Serializable;

public class CompeerMessage implements Serializable {
    private final User sender;
    private final CompeerType compeerType;
    private final String receiver ;
    public CompeerMessage(User sender,String receiver,CompeerType compeerType){
        this.receiver=receiver;
        this.sender =sender;
        this.compeerType=compeerType;
    }

    public User getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public CompeerType getCompeerType() {
        return compeerType;
    }

}
