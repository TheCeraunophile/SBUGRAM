package Messages.Requests;

import java.io.Serializable;

public class CommentMessage implements Serializable {
    private final User postWriter;
    private final User commentWriter;
    private final String text;
    private final Post postTarget;
    public CommentMessage(Post postTarget, User postWriter, User commentWriter, String text){
        this.postTarget =postTarget;
        this.postWriter =postWriter;
        this.text=text;
        this.commentWriter =commentWriter;
    }

    public User getPostWriter() {
        return postWriter;
    }

    public User getCommentWriter() {
        return commentWriter;
    }

    public String getText() {
        return text;
    }

    public String usernameOfCommentWriter(){
        return this.commentWriter.getUsername();
    }

    public String usernameOfPostWriter(){
        return this.postWriter.getUsername();
    }

    public Post getPostTarget() {
        return postTarget;
    }
}
