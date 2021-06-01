package Messages.ResivedMessages;
import Server.Databace;
import Messages.InformationToShow.User;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectedMessage implements ResivedMessage , Serializable {

    String username;
    String password;

    public ConnectedMessage(String password,String username){
        this.username=username;
        this.password=password;
    }

    private boolean connectionValid(){
        List<User> temp = Databace.members.stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username))
                .filter(a -> a.getPassword().equals(password)).collect(Collectors.toList());
        return temp.size() == 1;
    }

    @Override
    public void Allert() {
        if (connectionValid()){
            List<User> temp = Databace.members.stream().filter(a -> a.getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
            // TODO: 27/05/2021 throwing this user to client temp.get(0);
        }
    }
}
