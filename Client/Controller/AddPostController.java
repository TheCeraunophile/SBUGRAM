package Client.Controller;
import Messages.Requests.PostMessage;
import Messages.Requests.Refresh;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import Messages.Requests.ReplyMessage;
import Messages.Requests.User;
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
            if (DetailsOfClient.getTarget()==null){
                creatingPost();
            }else {
                if (DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())) {
                    creatingPost();
                }
            }
            if (!DetailsOfClient.getProfile().equals(DetailsOfClient.getTarget())){
                creatingRep();
            }
        }
    }

    private void update(){
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

    private void creatingRep(){
        ReplyMessage packet ;
        if (!titleOfPost.getText().equals("")){
            packet = new ReplyMessage(ProfileController.getPost(),DetailsOfClient.getTarget(),DetailsOfClient.getProfile(),titleOfPost.getText()+"\n"+textOfPost.getText());
        }
        else {
            packet = new ReplyMessage(ProfileController.getPost(),DetailsOfClient.getTarget(),DetailsOfClient.getProfile(),textOfPost.getText());
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
