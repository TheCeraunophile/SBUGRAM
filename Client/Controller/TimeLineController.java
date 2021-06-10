package Client.Controller;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.Disconnect;
import javafx.scene.control.Button;
import java.io.IOException;

public class TimeLineController{

    public Button logout;
    public Button home;
    public Button direct;
    public Button profile;
    public Button writPost;

    public void logout(){
        try {
            Disconnect disconnect = new Disconnect(DetailsOfClient.getUsername());
            DetailsOfClient.oos.writeObject(disconnect);
            DetailsOfClient.oos.flush();
//            DetailsOfClient.oos.close();
//            DetailsOfClient.ois.close();
            DetailsOfClient.setProfile(null);
            DetailsOfClient.setUsername("");
            new PageLoader().load("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WritPost(){
        try {
            new PageLoader().load("AddPost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void home(){

    }

    public void direct() {

    }

    public void profile(){

    }

}
