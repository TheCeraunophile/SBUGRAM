package Client.Controller;
import Messages.Requests.*;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;

public class AddPostController {

    public Button publish;
    public TextArea textOfPost;
    public Button cancel;
    public TextField titleOfPost;

    public void publish(){
        if(!textOfPost.getText().equals("")){
            if (DetailsOfClient.getTarget().equals(DetailsOfClient.getProfile())){
                creatingPost();
            }else {
                creatingComment();
            }
        }
    }

    private void update(){
        try {
            String username = DetailsOfClient.getUsername();
            String password = DetailsOfClient.getProfile().getPassword();
            User target = DetailsOfClient.getTarget();
            DetailsOfClient.oos.writeObject(new Disconnect(DetailsOfClient.getUsername(),true));
            DetailsOfClient.oos.flush();
            DetailsOfClient.closingSrc();
            DetailsOfClient.init();
            Connect packet = new Connect(username,password,true);
            DetailsOfClient.oos.writeObject(packet);
            DetailsOfClient.oos.flush();
            var answer = DetailsOfClient.ois.readObject();
            if (answer!=null){
                DetailsOfClient.setProfile((User)answer);
                DetailsOfClient.setTarget(target);
                DetailsOfClient.setUsername(username);
            }
        }catch (ClassNotFoundException e){
            e.getStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        if (DetailsOfClient.getTarget()==null)
            goToBack("TimeLine");
        if (DetailsOfClient.getTarget().equals(DetailsOfClient.getProfile()))
            goToBack("TimeLine");
        goToBack("Profile");
    }

    private void goToBack(String url){
        try {
            new PageLoader().load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creatingPost(){
        PostMessage packet  ;
        if (!titleOfPost.getText().equals("")){
            packet = new PostMessage(DetailsOfClient.getUsername(), titleOfPost.getText() + "\n" + textOfPost.getText());
        }
        else {
            packet = new PostMessage(DetailsOfClient.getUsername(),textOfPost.getText());
        }
        try {
            DetailsOfClient.oos.writeObject(packet);
            DetailsOfClient.oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        update();
        goToBack("TimeLine");
    }

    private void creatingComment(){
        CommentMessage packet ;
        if (!titleOfPost.getText().equals("")){
            packet = new CommentMessage(ProfileController.getPost(),DetailsOfClient.getTarget(),DetailsOfClient.getProfile(),titleOfPost.getText()+"\n"+textOfPost.getText());
        }
        else {
            packet = new CommentMessage(ProfileController.getPost(),DetailsOfClient.getTarget(),DetailsOfClient.getProfile(),textOfPost.getText());
        }
        try {
            DetailsOfClient.oos.writeObject(packet);
            DetailsOfClient.oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        update();
        goToBack("Profile");
    }
}
