package Client.Controller;

import Client.Model.PageLoader;
import Messages.Requests.Post;
import javafx.event.ActionEvent;
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
        title.setText(post.getText());
//        String temp = post.getText();
//        title.setText(temp.substring(0,temp.indexOf("\n")));
//        description.setText(temp.substring(temp.indexOf("\n"+1)));
//        profileImage.setImage(new Image(Paths.get("images/ali_alavi.jpg").toUri().toString()));
        return root;
    }
}