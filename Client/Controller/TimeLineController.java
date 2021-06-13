package Client.Controller;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.Disconnect;
import Messages.Requests.Post;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class TimeLineController{

    public Button logout;
    public Button home;
    public Button direct;
    public Button profile;
    public Button writPost;
    public ListView<Post> ListView;
    private ArrayList<Post> timeLine = new ArrayList<>();

    @FXML
    public void initialize() {
        try {
            System.out.println(DetailsOfClient.getProfile().getUsername());
            System.out.println(DetailsOfClient.getProfile().getPassword());
            for (int i=0;i<DetailsOfClient.profile.getPostList().size();i++){
                System.out.println(DetailsOfClient.getProfile().getPostList().get(i).getText());
            }
        }catch (Exception e){}
        for (int i=0;i<DetailsOfClient.profile.getFollowing().size();i++){
            timeLine.addAll(DetailsOfClient.getProfile().getFollowing().get(i).getPostList());
        }
        ListView.setItems(FXCollections.observableArrayList(timeLine));
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
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void direct() {

    }

    public void profile(){
        try {
            new PageLoader().load("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPost(MouseEvent mouseEvent) {
        Post p = ListView.getSelectionModel().getSelectedItem();
        if (p != null) {
            DetailsOfClient.setTarget(p.getSender());
        }
    }
}