package Client.Controller;

import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.CompeerMessage;
import Messages.Requests.CompeerType;
import Messages.Requests.Post;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileController {
    public Button back;
    public Button follow;
    public Button unfollow;
    public Button Direct;
    public Label follower_number;
    public Label following_number;
    public ListView<Post> listView;
    private final ArrayList<Post> profile = new ArrayList<>();
    public Label numberOfFollower;
    public Label numberOfFollowing;

    @FXML
    public void initialize() {
        numberOfFollower.setText("12");
        profile.addAll(DetailsOfClient.getProfile().getPostList());
        listView.setItems(FXCollections.observableArrayList(profile));
        listView.setCellFactory(ListView -> new PostItem());
    }

    public void backToTheTimeLine(){
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startingOfFollow(){
        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(),DetailsOfClient.getTarget(), CompeerType.FOLLOW);
        }
    }

    public void endOfFollow(){

        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(),DetailsOfClient.getTarget(), CompeerType.UNFOLLOW);
        }
    }

    public void goToCurrenrUserDirect(){

    }

    public void showPost(MouseEvent mouseEvent) {
        Post p = listView.getSelectionModel().getSelectedItem();
        if (p!=null)
            System.out.println(p.getText());
    }
}