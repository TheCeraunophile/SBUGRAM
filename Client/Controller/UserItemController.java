package Client.Controller;

import Client.Model.PageLoader;
import Messages.Requests.Post;
import Messages.Requests.User;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserItemController {

    public AnchorPane root;
    public Label username;
    public Label bio;
    User user;

    public UserItemController(User user) throws IOException {
        new PageLoader().load("UserItem", this);
        this.user = user;
    }

    public AnchorPane init() {
        username.setText(user.getUsername());
        bio.setText(user.getBio());
        //        profileImage.setImage(new Image(Paths.get("images/ali_alavi.jpg").toUri().toString()));
        return root;
    }
}
