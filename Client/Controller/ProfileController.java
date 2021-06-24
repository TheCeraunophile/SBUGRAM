package Client.Controller;

import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileController {

    //default options
    public Button back;
    public Button follow;
    public Button unfollow;
    public Button Direct;
    public Label follower_number;
    public Label following_number;
    public Label numberOfFollower;
    public Label numberOfFollowing;
    public Button Refresh;

    //for seen list of users that follow this profile or lists that this followed
    public ListView<User> followOrFollowingList;


    //for parse my profile with others if inMainPage equal True sign I am in my profile or Isn't sign in other profile
    private boolean inMainPage = true;

    //for detecting every post and like or dislike or rep
    private static Post post;

    //for going to profile in followers list or following list
    private static User target ;

    //for list view
    public ListView<Post> listView;

    //for every post
    public Button like;
    public Button dislike;
    public Button AddComment;
    //---
    public Label LIKE_TEXT;
    public Label NUMBER_LIKE;
    public Label DISLIKE_TEXT;
    public Label NUMBER_DISLIKE;
    public Label COMMENT_TEXT;
    public Label NUMBER_COM;

    @FXML
    public void initialize() {
        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            if (DetailsOfClient.getProfile().getFollowing().contains(DetailsOfClient.getTarget())){
                unfollow.setVisible(true);
            }else {
                follow.setVisible(true);
            }
        }
        numberOfFollower.setText(Integer.toString(DetailsOfClient.getTarget().getFollower().size()));
        numberOfFollowing.setText(Integer.toString(DetailsOfClient.getTarget().getFollowing().size()));
        List<Post> profile = new ArrayList<>(DetailsOfClient.getTarget().getPostList());
        profile = profile.stream().sorted(Comparator.comparing(Post::getPublishDate).reversed()).collect(Collectors.toList());
        listView.setItems(FXCollections.observableArrayList(profile));
        listView.setCellFactory(ListView -> new PostItem());
    }

    public void backToTheTimeLine(){
        like.setVisible(false);
        dislike.setVisible(false);
        AddComment.setVisible(false);
        follow.setVisible(false);
        unfollow.setVisible(false);
        LIKE_TEXT.setVisible(false);
        DISLIKE_TEXT.setVisible(false);
        COMMENT_TEXT.setVisible(false);
        if(inMainPage) {
            goToPage("TimeLine");
        }else {
            inMainPage =true;
            goToPage("Profile");
        }
    }

    public void startingOfFollow(){
        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(),DetailsOfClient.getTarget().getUsername(), CompeerType.FOLLOW,false);
            sendingCompeerMessage(packet);
        }
    }

    public void endOfFollow(){
        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(),DetailsOfClient.getTarget().getUsername(), CompeerType.UNFOLLOW,false);
            sendingCompeerMessage(packet);
        }
    }

    private void sendingCompeerMessage(CompeerMessage packet){
            DetailsOfClient.writeObject(packet);
    }

    public void showPost(MouseEvent mouseEvent) {
        post = listView.getSelectionModel().getSelectedItem();
        if (post != null) {
            NUMBER_LIKE.setText(post.getLike().toString());
            NUMBER_DISLIKE.setText(post.getDisLike().toString());
//            NUMBER_REP.setText(post.getComment().toString());
            inMainPage =false;
            like.setVisible(true);
            dislike.setVisible(true);
            AddComment.setVisible(true);
            NUMBER_DISLIKE.setVisible(true);
            NUMBER_LIKE.setVisible(true);
            NUMBER_COM.setVisible(true);
            LIKE_TEXT.setVisible(true);
            DISLIKE_TEXT.setVisible(true);
            COMMENT_TEXT.setVisible(true);
            DetailsOfClient.setTarget(post.getSender());
            try {
                List<Post> temp = new ArrayList<>(post.getComments());
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
            DetailsOfClient.writeObject(packet);
        }
    }

    public void updateLike(){
        if (post!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(post.getSender(),post,1);
            DetailsOfClient.writeObject(packet);
        }
    }

    public void addAComment(){
        if (post!=null){
            DetailsOfClient.setTarget(post.getSender());
            goToPage("AddPost");
        }
    }

    public void goToCurrentUserDirect(){
        // TODO: 23/06/2021
    }

    /**
     * get post used when we want to add a comment under one selected post and called in AdPostController
     * */

    public static Post getPost() {
        return post;
    }

    /**
     * these added to show list of followers or following in every user profile
     * */


    public void showFollowersList(){
        cleanDetailsOfPost();
        followOrFollowingList.setVisible(true);
        ArrayList<User> followOrFollowingArrayList = new ArrayList<>(DetailsOfClient.getProfile().getFollowing());
        followOrFollowingList.setItems(FXCollections.observableArrayList(followOrFollowingArrayList));
        followOrFollowingList.setCellFactory(resultSearch -> new UserItem());
    }

    public void shoFollowingList(){
        cleanDetailsOfPost();
        followOrFollowingList.setVisible(true);
        ArrayList<User> followOrFollowingArrayList = new ArrayList<>(DetailsOfClient.getProfile().getFollower());
        followOrFollowingList.setItems(FXCollections.observableArrayList(followOrFollowingArrayList));
        followOrFollowingList.setCellFactory(resultSearch -> new UserItem());
    }

    private void cleanDetailsOfPost(){
        inMainPage =true;
        like.setVisible(false);
        dislike.setVisible(false);
        AddComment.setVisible(false);
        NUMBER_DISLIKE.setVisible(false);
        NUMBER_LIKE.setVisible(false);
        NUMBER_COM.setVisible(false);
        LIKE_TEXT.setVisible(false);
        DISLIKE_TEXT.setVisible(false);
        COMMENT_TEXT.setVisible(false);
    }

    public void shoProfileOfUser(){
        target = followOrFollowingList.getSelectionModel().getSelectedItem();
        if (target!=null){
            DetailsOfClient.setTarget(target);
            goToPage("Profile");
        }
    }

    public void Refresh(){
        cleanDetailsOfPost();
        String username = DetailsOfClient.getUsername();
        String password = DetailsOfClient.getProfile().getPassword();
        String passwordTarget = DetailsOfClient.getTarget().getUsername();
        DetailsOfClient.writeObject(new Disconnect(DetailsOfClient.getUsername(),true));
        DetailsOfClient.closingSrc();
        DetailsOfClient.init();
        Connect packet = new Connect(username,password,true);
        DetailsOfClient.writeObject(packet);
        var answer = DetailsOfClient.readObject();
        if (answer!=null){
            DetailsOfClient.setProfile((User)answer);
            DetailsOfClient.setTarget((User) answer);
            DetailsOfClient.setUsername(username);
            DetailsOfClient.settings();
        }
        goToPage("TimeLine");
    }

    private void goToPage(String uri){
        try {
            new PageLoader().load(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
