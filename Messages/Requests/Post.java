package Messages.Requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Post implements Serializable {

    private final User sender;
    private final String text;
    private Integer like;
    private Integer disLike;
    private List<Post> replysOfPost = new ArrayList<>();

    public Post(User sender,String text){
        this.sender=sender;
        this.text=text;
        like=0;
        disLike=0;
    }

    private void replyForPost(Post post){
        replysOfPost.add(post);
    }

    public void updateDisLike(){
        disLike++;
    }

    public void updateLike(){
        like++;
    }

    public String getText() {
        return text;
    }

    public User getSender() {
        return sender;
    }
}