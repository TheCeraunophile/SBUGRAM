package Client.Controller;
import Server.Databace.Post;
import Client.Model.DetailsOfClient;
import Client.Model.PageLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.io.IOException;

public class AddPostController {

    public Button publish;
    public TextArea textOfPost;
    public Button cancel;

    public void publish(){
        if(!textOfPost.getText().equals("")){
            Post post = new Post(DetailsOfClient.getProfile(), textOfPost.getText());
            try {
                DetailsOfClient.oos.writeObject(post);
                DetailsOfClient.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jumpToBack();
        }else {
            // TODO: 09/06/2021 one popup
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
