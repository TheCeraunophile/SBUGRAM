package Messages.ResivedMessages;
import Server.Databace;
import Messages.InformationToShow.*;
import java.io.Serializable;

public class LikeMessage implements ResivedMessage , Serializable {

    private User resived;
    private Post post;

    @Override
    public void Allert() {
        pushingData();
    }

    private void pushingData(){
        resived.getPostList().get(resived.getPostList().indexOf(post)).updateLike();
    }
}
