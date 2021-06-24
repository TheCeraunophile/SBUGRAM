package Messages.Requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Post implements Serializable {

    private final User sender;
    private final String text;
    private Integer NUMBER_COMMENTS;
    private Integer like;
    private Integer disLike;
    private final List<Post> comments = new ArrayList<>();
    private final Date publishDate ;
    public Post(User sender,String text){
        this.sender=sender;
        this.text=text;
        like=0;
        disLike=0;
        NUMBER_COMMENTS =0;
        publishDate = new Date();
    }

    public void addComment(Post post){
        comments.add(post);
        NUMBER_COMMENTS = comments.size();
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

    public List<Post> getComments(){
        return comments;
    }

    public Integer getDisLike() {
        return disLike;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getNUMBER_COMMENTS() {
        return NUMBER_COMMENTS;
    }

    public Date getPublishDate() {
        return publishDate;
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