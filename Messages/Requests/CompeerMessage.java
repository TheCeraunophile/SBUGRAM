package Messages.Requests;

public class CompeerMessage {
    private final User sender;
    private final CompeerType compeerType;
    private final User receiver ;
    public CompeerMessage(User user,User receiver,CompeerType compeerType){
        this.receiver=receiver;
        this.sender =user;
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
