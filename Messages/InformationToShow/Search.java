package Messages.InformationToShow;
import Server.Databace;
import java.util.List;
import java.util.stream.Collectors;

public class Search {
    String username;

    public Search(String username){
        this.username=username;
    }

    public List<User> resultSearch(){
        return Databace.members.stream().filter(a -> a.getUsername().equals(this.username)).collect(Collectors.toList());
    }

}
