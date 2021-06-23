package Messages.Requests;

import java.io.Serializable;

public class CompeerMessage implements Serializable {
    private final User sender;
    private final CompeerType compeerType;
    private final String receiver ;
    private final boolean update ;
    public CompeerMessage(User sender,String receiver,CompeerType compeerType,boolean update){
        this.receiver = receiver;
        this.sender = sender;
        this.compeerType = compeerType;
        this.update = update;
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

    public boolean getUpdate(){
        return this.update;
    }
}
