package Client.Controller;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeLineController{

    //for main menu
    public Button logout;
    public Button home;
    public Button direct;
    public Button profile;
    public Button writPost;
    public ListView<Post> ListView;
    private ArrayList<Post> timeLine ;

    //for help to the searching
    public TextField textOfSearch;
    public Button startOfSearch;
    public Button clear;
    public ListView<User> resultSearch ;

    //every attempt for every post
    public Button like;
    public Button dislike;
    public Button comment;

    //for show all of attempt for every post
    public Label like_label;
    public Label dislike_label;
    public Label comment_label;
    public Label number_like;
    public Label number_dislike;
    public Label number_comment;
    public Button goToProfileOwner;

    //for detect we clicked who on post?
    private static Post postTarget = null;

    //for show we see some comment of one page or see our TimeLine
    private static boolean  inmainPage = true;

    @FXML
    public void initialize() {
        timeLine = new ArrayList<>();
        DetailsOfClient.settings();
        ArrayList<User> followings = new ArrayList<>(DetailsOfClient.getProfile().getFollowing());
        for (int i=0;i<DetailsOfClient.profile.getFollowing().size();i++){
            timeLine.addAll(followings.get(i).getPostList());
        }
        
        //reverse sorting
        int j ;
        for (int i=0;i<timeLine.size()/2;i++){
            j = timeLine.size()-i-1;
            if (i>=j){
                break;
            }
            Post temp = timeLine.get(i);
            timeLine.set(i,timeLine.get(j));
            timeLine.set(j,temp);
        }
        
        ListView.setItems(FXCollections.observableArrayList(timeLine));
        ListView.setCellFactory(ListView -> new PostItem());
        resultSearch.setVisible(false);
        clear.setVisible(false);
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
            DetailsOfClient.setTarget(DetailsOfClient.getProfile());
            new PageLoader().load("AddPost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //home method like refresh in TimeLine
    public void home(){
        dislike.setVisible(false);
        like.setVisible(false);
        comment.setVisible(false);
        number_comment.setVisible(false);
        number_dislike.setVisible(false);
        number_like.setVisible(false);
        like_label.setVisible(false);
        dislike_label.setVisible(false);
        comment_label.setVisible(false);
        goToProfileOwner.setVisible(false);
        try {
            String username = DetailsOfClient.getUsername();
            String password = DetailsOfClient.getProfile().getPassword();
            DetailsOfClient.oos.writeObject(new Disconnect(DetailsOfClient.getUsername()));
            DetailsOfClient.oos.flush();
            DetailsOfClient.closingSrc();
            DetailsOfClient.init();
            Connect packet = new Connect(username,password,true);
            DetailsOfClient.oos.writeObject(packet);
            DetailsOfClient.oos.flush();
            var answer = DetailsOfClient.ois.readObject();
            if (answer!=null){
                DetailsOfClient.setProfile((User)answer);
                DetailsOfClient.setUsername(username);
                DetailsOfClient.settings();
                new PageLoader().load("TimeLine");
            }
        }catch (ClassNotFoundException e){
            e.getStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void direct() {
        // TODO: 23/06/2021  
    }

    public void profile(){
        DetailsOfClient.setTarget(DetailsOfClient.getProfile());
        try {
            new PageLoader().load("Profile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this 2 methods to finding others users and go to their direct or profile
     * clear result , hide the window that show results
     * showListResult goes to the selected User profile
     * */

    public void Searching(){
        if (!textOfSearch.getText().equals("")){
            dislike.setVisible(false);
            like.setVisible(false);
            comment.setVisible(false);
            number_comment.setVisible(false);
            number_dislike.setVisible(false);
            number_like.setVisible(false);
            like_label.setVisible(false);
            dislike_label.setVisible(false);
            comment_label.setVisible(false);
            goToProfileOwner.setVisible(false);
            SearchMessage packet = new SearchMessage(textOfSearch.getText());
            try {
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
                List<User> innerListUser = new ArrayList<>();
                var answer = DetailsOfClient.ois.readObject();
                while (answer!=null){
                    innerListUser.add((User) answer);
                    answer = DetailsOfClient.ois.readObject();
                }
                clear.setVisible(true);
                resultSearch.setVisible(true);
                startOfSearch.setVisible(false);
                Set<User> temp = new HashSet<>(innerListUser);
                resultSearch.setItems(FXCollections.observableArrayList(temp));
                resultSearch.setCellFactory(resultSearch -> new UserItem());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearResultSearch(){
        resultSearch.setVisible(false);
        clear.setVisible(false);
        startOfSearch.setVisible(true);
        textOfSearch.setText("");
    }

    public void showListUser(){
        User u = resultSearch.getSelectionModel().getSelectedItem();
        DetailsOfClient.setTarget(u);
        if (u!=null) {
//            DetailsOfClient.setTarget(resultSearch.getSelectionModel().getSelectedItem());
            try {
                new PageLoader().load("Profile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 4 button for working with others post ,like,dislike,rep and back to seen others posts
     * if one post selected it's reply showed in time line and with back button this turn to past.
     * this showPost method point to the selected post in post list of time line NOT search results.
     * */

    public void showPost(MouseEvent mouseEvent) {
        Post p = ListView.getSelectionModel().getSelectedItem();
        postTarget=p;
        if (p != null) {
            number_like.setText(p.getLike().toString());
            number_dislike.setText(p.getDisLike().toString());
            number_comment.setText(p.getComment().toString());
            dislike.setVisible(true);
            like.setVisible(true);
            comment.setVisible(true);
            number_comment.setVisible(true);
            number_dislike.setVisible(true);
            number_like.setVisible(true);
            like_label.setVisible(true);
            dislike_label.setVisible(true);
            comment_label.setVisible(true);
            goToProfileOwner.setVisible(true);
            DetailsOfClient.setTarget(p.getSender());
            try {
                List<Post> temp = new ArrayList<>(p.getListReply());
                temp.add(0,p);
                ListView.setItems(FXCollections.observableArrayList(temp));
                ListView.setCellFactory(ListView -> new PostItem());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLikeOfPostIsOthers(){
        if (postTarget!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(postTarget.getSender(),postTarget,1);
            try {
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDislikeOfPostIsOthers(){
        if (postTarget!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(postTarget.getSender(),postTarget,0);
            try {
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void replyThisPost(){
        if (postTarget!=null){
            DetailsOfClient.setTarget(postTarget.getSender());
            try {
                new PageLoader().load("AddPost");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToProfileOfOwner(){
        DetailsOfClient.setTarget(postTarget.getSender());
        try {
            new PageLoader().load("Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}