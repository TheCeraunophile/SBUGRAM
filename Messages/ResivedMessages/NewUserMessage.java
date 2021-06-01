package ResivedMessages;
import DataCenter.Databace;
import Exceptions.UsernameDontValid;
import InformationToShow.User;

import java.io.Serializable;
import java.util.Date;

public class NewUserMessage implements ResivedMessage , Serializable {

    private String username;
    private String password;
    private Date birthdayDate;
    private String bio;

    public NewUserMessage(String username,String password,Date birthdayDate,String bio){
        this.username=username;
        this.password=password;
        this.birthdayDate=birthdayDate;
        this.bio=bio;
    }

    public void pushingNewUserDetails(){
        User user = new User(username,password,birthdayDate,bio);
        Databace.members.add(user);
    }

    @Override
    public void Allert() throws UsernameDontValid {
        if (session()) {
            pushingNewUserDetails();
            ConnectedMessage connectedMessage = new ConnectedMessage(password,username);
            connectedMessage.Allert();
        }
        throw new UsernameDontValid("this username already exist");
            // TODO: 27/05/2021 get some inform in consoll
    }

    private boolean session(){
        return Databace.members.stream().noneMatch(a -> a.getUsername().equalsIgnoreCase(username));
    }
}
