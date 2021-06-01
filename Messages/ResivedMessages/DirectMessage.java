package ResivedMessages;
import DataCenter.Databace;
import InformationToShow.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DirectMessage implements ResivedMessage , Serializable {
    private User sender;
    private User risived;
    private String textMessage;

    private void pushingDirectMessage(){
        sender.updateDirectMessage(risived,textMessage);
        risived.updateDirectMessage(sender,textMessage);
    }

    @Override
    public void Allert() {

    }
}
