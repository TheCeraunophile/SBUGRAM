package Messages.ResivedMessages;

import Messages.InformationToShow.*;
import java.io.Serializable;

public class DissLikeMessage implements ResivedMessage , Serializable {
    private User resived ;
    private Post post;

    @Override
    public void Allert() {

    }

    private void pushingData(){
        resived.getPostList().get(resived.getPostList().indexOf(post)).updateDisLike();
    }
}
