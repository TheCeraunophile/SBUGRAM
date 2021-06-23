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
import java.util.List;

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
    public Button reply;
    public Label NUMBER_LIKE;
    public Label NUMBER_DISLIKE;
    public Label NUMBER_REP;
    //---
    public Label like_text;
    public Label dislike_test;
    public Label rep_test;

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
        ArrayList<Post> profile = new ArrayList<>(DetailsOfClient.getTarget().getPostList());
        int j ;
        for (int i=0;i<profile.size()/2;i++){
            j = profile.size()-i-1;
            if (i>=j){
                break;
            }
            Post temp = profile.get(i);
            profile.set(i,profile.get(j));
            profile.set(j,temp);
        }
        listView.setItems(FXCollections.observableArrayList(profile));
        listView.setCellFactory(ListView -> new PostItem());
    }

    public void backToTheTimeLine(){
        like.setVisible(false);
        dislike.setVisible(false);
        reply.setVisible(false);
        follow.setVisible(false);
        unfollow.setVisible(false);
        like_text.setVisible(false);
        dislike_test.setVisible(false);
        rep_test.setVisible(false);
        if(inMainPage) {
            goToPage("TimeLine");
        }else {
            inMainPage =true;
            goToPage("Profile");
        }
    }

    public void startingOfFollow(){
        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(),DetailsOfClient.getTarget().getUsername(), CompeerType.FOLLOW);
            sendingCompeerMessage(packet);
        }
    }

    public void endOfFollow(){
        if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
            CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(),DetailsOfClient.getTarget().getUsername(), CompeerType.UNFOLLOW);
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
            reply.setVisible(true);
            NUMBER_DISLIKE.setVisible(true);
            NUMBER_LIKE.setVisible(true);
            NUMBER_REP.setVisible(true);
            like_text.setVisible(true);
            dislike_test.setVisible(true);
            rep_test.setVisible(true);
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
            DetailsOfClient.writeObject(packet);
        }
    }

    public void updateLike(){
        if (post!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(post.getSender(),post,1);
            DetailsOfClient.writeObject(packet);
        }
    }

    public void updateReply(){
        if (post!=null){
            DetailsOfClient.setTarget(post.getSender());
            goToPage("AddPost");
        }
    }

    public void goToCurrentUserDirect(){
        // TODO: 23/06/2021
    }

    public static Post getPost() {
        return post;
    }

    /**
     * these added to show list of followers or following in every user profile
     * */


    public void showFollowersList(){
        cleanDetailsOfPost();
        followOrFollowingList.setVisible(true);
        ArrayList<User> followOrFollowingArrayList = new ArrayList<>(DetailsOfClient.getProfile().getFollower());
        followOrFollowingList.setItems(FXCollections.observableArrayList(followOrFollowingArrayList));
        followOrFollowingList.setCellFactory(resultSearch -> new UserItem());
    }

    public void shoFollowingList(){
        cleanDetailsOfPost();
        followOrFollowingList.setVisible(true);
        ArrayList<User> followOrFollowingArrayList = new ArrayList<>(DetailsOfClient.getProfile().getFollowing());
        followOrFollowingList.setItems(FXCollections.observableArrayList(followOrFollowingArrayList));
        followOrFollowingList.setCellFactory(resultSearch -> new UserItem());
    }

    private void cleanDetailsOfPost(){
        inMainPage =true;
        like.setVisible(false);
        dislike.setVisible(false);
        reply.setVisible(false);
        NUMBER_DISLIKE.setVisible(false);
        NUMBER_LIKE.setVisible(false);
        NUMBER_REP.setVisible(false);
        like_text.setVisible(false);
        dislike_test.setVisible(false);
        rep_test.setVisible(false);
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
        DetailsOfClient.writeObject(new Disconnect(DetailsOfClient.getUsername()));
        DetailsOfClient.closingSrc();
        DetailsOfClient.init();
        Connect packet = new Connect(username,password);
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
