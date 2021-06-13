package Client.Controller;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.Disconnect;
import Messages.Requests.Post;
import Messages.Requests.Refresh;
import Messages.Requests.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class TimeLineController{

    public Button logout;
    public Button home;
    public Button direct;
    public Button profile;
    public Button writPost;
    public ListView<Post> ListView;

    @FXML
    public void initialize() {
        ListView.setItems(FXCollections.observableArrayList(DetailsOfClient.getProfile().getPostList()));
        ListView.setCellFactory(ListView -> new PostItem());
    }

    public void logout(){
        try {
            Disconnect disconnect = new Disconnect(DetailsOfClient.getUsername());
            DetailsOfClient.oos.writeObject(disconnect);
            DetailsOfClient.oos.flush();
            DetailsOfClient.closingSrc();
            DetailsOfClient.init();
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
        try {
            DetailsOfClient.oos.writeObject(new Refresh(DetailsOfClient.getUsername()));
            DetailsOfClient.oos.flush();
            var answer = DetailsOfClient.ois.readObject();
            DetailsOfClient.setProfile((User) answer);
        }catch (ClassNotFoundException e){
            e.getStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void direct() {

    }

    public void profile(){

    }
}