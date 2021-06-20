package Client.Controller;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.*;
import Server.Databace.Databace;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeLineController{

    public Button logout;
    public Button home;
    public Button direct;
    public Button profile;
    public Button writPost;
    public ListView<Post> ListView;
    public TextField textOfSearch;
    public Button startOfSearch;
    public ListView<User> resultSearch ;
    public Button clear;
    public Button like;
    public Button dislike;
    public Button back;
    public Button reply;
    private ArrayList<Post> timeLine = new ArrayList<>();
    private static boolean  inmainPage = true;
    private static Post postTarget = null;

    @FXML
    public void initialize() {
        if (Databace.getInstance().cache!=null)
        System.out.println("clients is"+Databace.getInstance().cache.size());
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
        DetailsOfClient.setTarget(DetailsOfClient.getProfile());
        try {
            new PageLoader().load("AddPost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void home(){
        back.setVisible(false);
        dislike.setVisible(false);
        like.setVisible(false);
        reply.setVisible(false);
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void direct() {

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
     * */

    public void Searching(){
        if (!textOfSearch.getText().equals("")){
            SearchMessage packet = new SearchMessage(textOfSearch.getText());
            try {
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
                List<User> ll = new ArrayList<>();
                var answer = DetailsOfClient.ois.readObject();
                while (answer!=null){
                    ll.add((User) answer);
                    answer = DetailsOfClient.ois.readObject();
                }
                System.out.println(ll.size());
                clear.setVisible(true);
                resultSearch.setVisible(true);
                startOfSearch.setVisible(false);
                Set<User> temp = new HashSet<>(ll);
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

    /**
     * 4 button for working with others post ,like,dislike,rep and back to seen others posts
     * if one post selected it's reply showed in time line and with back button this turn to past.
     * this showPost method point to the selected post in post list of time line NOT search results.
     * */

    public void showPost(MouseEvent mouseEvent) {
        Post p = ListView.getSelectionModel().getSelectedItem();
        postTarget=p;
        if (p != null) {
            dislike.setVisible(true);
            like.setVisible(true);
            reply.setVisible(true);
            back.setVisible(true);
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
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(postTarget.getSender(),postTarget,0);
            try {
                DetailsOfClient.oos.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDislikeOfPostIsOthers(){
        if (postTarget!=null){
            LikeOrDislikeMessage packet = new LikeOrDislikeMessage(postTarget.getSender(),postTarget,1);
            try {
                DetailsOfClient.oos.writeObject(packet);
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
}