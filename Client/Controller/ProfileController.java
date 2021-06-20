package Client.Controller;

import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.CompeerMessage;
import Messages.Requests.CompeerType;
import Messages.Requests.LikeOrDislikeMessage;
import Messages.Requests.Post;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public Button like;
    public Button dislike;
    public Button reply;

    private boolean inmainPage = true;
    private static Post post;
    @FXML
    public void initialize() {
        numberOfFollower.setText(Integer.toString(DetailsOfClient.getTarget().getFollower().size()));
        numberOfFollowing.setText(Integer.toString(DetailsOfClient.getTarget().getFollowing().size()));
        profile.addAll(DetailsOfClient.getTarget().getPostList());
//        listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(profile));
        listView.setCellFactory(ListView -> new PostItem());
    }

    public void backToTheTimeLine(){
        like.setVisible(false);
        dislike.setVisible(false);
        reply.setVisible(false);
        try {
            if(inmainPage) {
                new PageLoader().load("TimeLine");
            }else {
                inmainPage=true;
                System.out.println("lll");
                new PageLoader().load("Profile");
            }
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

    public void showPost(MouseEvent mouseEvent) {
        post = listView.getSelectionModel().getSelectedItem();
        if (post != null) {
            inmainPage=false;
            like.setVisible(true);
            dislike.setVisible(true);
            reply.setVisible(true);
            DetailsOfClient.setTarget(post.getSender());
            try {
                List<Post> temp = new ArrayList<>(post.getListReply());
                temp.add(0,post);
                listView.setItems(FXCollections.observableArrayList(temp));
                listView.setCellFactory(ListView -> new PostItem());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDislike(){
        if (post!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(post.getSender(),post,0);
            try {
                DetailsOfClient.oos.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLike(){
        if (post!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(post.getSender(),post,1);
            try {
                DetailsOfClient.oos.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateReply(){
        if (post!=null){
            DetailsOfClient.setTarget(post.getSender());
            try {
                new PageLoader().load("AddPost");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToCurrenrUserDirect(){

    }

    public static Post getPost() {
        return post;
    }
}
