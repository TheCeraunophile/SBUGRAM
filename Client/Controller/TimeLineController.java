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
import java.util.*;
import java.util.stream.Collectors;

public class TimeLineController{

    //for main menu
    public Button logout;
    public Button home;
    public Button direct;
    public Button profile;
    public Button writPost;
    public ListView<Post> ListView;
    private List<Post> timeLine ;

    //for help to the searching
    public TextField textOfSearch;
    public Button startOfSearch;
    public Button clear;
    public ListView<User> resultSearch ;

    //every attempt for every post
    public Button like;
    public Button dislike;
    public Button AddComment;

    //for show all of attempt for every post
    public Label LIKE_TEXT;
    public Label NUMBER_LIKE;
    public Label DISLIKE_TEXT;
    public Label NUMBER_DISLIKE;
    public Label COMMENT_TEXT;
    public Label NUMBER_COM;
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
        timeLine = timeLine.stream().sorted(Comparator.comparing(Post::getPublishDate).reversed()).collect(Collectors.toList());

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
        AddComment.setVisible(false);
        NUMBER_COM.setVisible(false);
        NUMBER_DISLIKE.setVisible(false);
        NUMBER_LIKE.setVisible(false);
        LIKE_TEXT.setVisible(false);
        DISLIKE_TEXT.setVisible(false);
        COMMENT_TEXT.setVisible(false);
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
            AddComment.setVisible(false);
            NUMBER_COM.setVisible(false);
            NUMBER_DISLIKE.setVisible(false);
            NUMBER_LIKE.setVisible(false);
            LIKE_TEXT.setVisible(false);
            DISLIKE_TEXT.setVisible(false);
            COMMENT_TEXT.setVisible(false);
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
            NUMBER_LIKE.setText(p.getLike().toString());
            NUMBER_DISLIKE.setText(p.getDisLike().toString());
            NUMBER_COM.setText(p.getNUMBER_COMMENTS().toString());
            dislike.setVisible(true);
            like.setVisible(true);
            AddComment.setVisible(true);
            NUMBER_COM.setVisible(true);
            NUMBER_DISLIKE.setVisible(true);
            NUMBER_LIKE.setVisible(true);
            LIKE_TEXT.setVisible(true);
            DISLIKE_TEXT.setVisible(true);
            COMMENT_TEXT.setVisible(true);
            goToProfileOwner.setVisible(true);
            DetailsOfClient.setTarget(p.getSender());
            try {
                List<Post> temp = new ArrayList<>(p.getComments());
                temp.add(0,p);
                ListView.setItems(FXCollections.observableArrayList(temp));
                ListView.setCellFactory(ListView -> new PostItem());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //just for test
            if (p.getComments().size()>0){
                System.out.println(p.getComments().get(0).getText()+ "  "+ p.getComments().get(0).getSender().getUsername());
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