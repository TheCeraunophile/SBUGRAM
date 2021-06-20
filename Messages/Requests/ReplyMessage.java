package Messages.Requests;

public class ReplyMessage {
    private User replyed ;
    private User replyer;
    private String text;
    private Post post;
    public ReplyMessage(Post post,User replyed,User replayer,String textOfReply){
        this.post=post;
        this.replyed=replyed;
        this.text=textOfReply;
        this.replyer=replayer;
    }

    public User getReplyed() {
        return replyed;
    }

    public User getReplyer() {
        return replyer;
    }

    public String getText() {
        return text;
    }

    public String usernameOfReplayer(){
        return this.replyer.getUsername();
    }

    public String usernameOfReplayed(){
        return this.replyed.getUsername();
    }

    public Post getPost() {
        return post;
    }
}
