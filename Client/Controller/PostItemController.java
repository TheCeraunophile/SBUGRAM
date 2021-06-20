package Client.Controller;

import Client.Model.PageLoader;
import Messages.Requests.Post;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class PostItemController {
    public AnchorPane root;
    public Label username;
    public Label title;
    public Label description;
    Post post;

    public PostItemController(Post post) throws IOException {
        new PageLoader().load("PostItem", this);
        this.post = post;
    }

    public AnchorPane init() {
        username.setText(post.getSender().getUsername());
        if (post.getText().contains("\n")){
            String temp = post.getText();
            int newline = temp.indexOf("\n");
            title.setText(temp.substring(0,newline));
            description.setText(temp.substring(newline+1));
        }
        else {
            title.setText(null);
            description.setText(post.getText());
        }
        //        profileImage.setImage(new Image(Paths.get("images/ali_alavi.jpg").toUri().toString()));
        return root;
    }
}