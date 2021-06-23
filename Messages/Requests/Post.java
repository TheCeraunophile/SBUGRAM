package Messages.Requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Post implements Serializable {

    private final User sender;
    private final String text;
    private Integer comment;
    private Integer like;
    private Integer disLike;
    private final List<Post> replysOfPost = new ArrayList<>();

    public Post(User sender,String text){
        this.sender=sender;
        this.text=text;
        like=0;
        disLike=0;
        comment=0;
    }

    public void addReply(Post post){
        replysOfPost.add(post);
        comment++;
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

    public List<Post> getListReply(){
        return replysOfPost;
    }

    public Integer getDisLike() {
        return disLike;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object obj){
        if (obj==null) {
            return false;
        }
        Post temp ;
        try {
            temp = (Post) obj;
        }catch (Exception e){
            return false;
        }
        if (!this.sender.getUsername().equalsIgnoreCase(temp.sender.getUsername()))
            return false;
        return this.text.equalsIgnoreCase(temp.text);
    }
}