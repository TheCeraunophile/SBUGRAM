package ResivedMessages;
import InformationToShow.User;

import java.io.Serializable;

public class DisconnectMessage implements ResivedMessage , Serializable {
    private User disconnected;

    private void closeResources(String username,String password){

    }

    @Override
    public void Allert() {

    }
}
