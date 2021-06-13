package Client.Controller;
import Messages.Requests.PostMessage;
import Messages.Requests.Refresh;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
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
            /*
                title of every post handled by only one new line
                title in not necessary for every post
            */
            PostMessage postMessage = new PostMessage(DetailsOfClient.getProfile().getUsername(), titleOfPost.getText() + "\n" + textOfPost.getText());
            try {
                DetailsOfClient.oos.writeObject(postMessage);
                DetailsOfClient.oos.flush();
                DetailsOfClient.oos.writeObject(new Refresh(DetailsOfClient.getUsername()));
                DetailsOfClient.oos.flush();
                var answer = DetailsOfClient.ois.readObject();
                DetailsOfClient.setProfile((User) answer);
            }catch (ClassNotFoundException e){
                e.getStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jumpToBack();
        }
    }

    private void jumpToBack(){
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        jumpToBack();
    }
}
