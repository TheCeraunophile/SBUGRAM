package Messages.Requests;

import Server.Databace.Post;
import Server.Databace.User;

import java.io.Serializable;

public class LikeOrDislikeMessage implements  Serializable {

    private final User resived ;
    private final Post post;
    private final Integer gread;

    public LikeOrDislikeMessage(User resived, Post post,Integer gread) {
        this.resived = resived;
        this.post = post;
        this.gread=gread;
    }

    public Post getPost() {
        return post;
    }

    public User getResived() {
        return resived;
    }

    public Integer getGread() {
        return gread;
    }
}
