package Messages.Requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String username;
    private String password;
    private String location;
    private String bio;
    private final ArrayList<Post> postList = new ArrayList<>();
    private final ArrayList<User> following = new ArrayList<>();
    private final ArrayList<User> follower = new ArrayList<>();
    private final HashMap<User,ArrayList<String>> directMessage = new HashMap<>();

    public User(String username,String password,String birthdayDate,String bio){
        this.username=username;
        this.password=password;
        this.location =birthdayDate;
        this.bio=bio;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public ArrayList<Post> getPostList (){
        return postList;
    }

    public void updatePost(String text){
        Post post = new Post(this,text);
        postList.add(post);
    }

    public void updateDirectMessage(User up, String textMessage){
        if (!directMessage.containsKey(up)){
            ArrayList<String> temp = new ArrayList<>();
            temp.add(textMessage);
            directMessage.put(up,temp);
        }else {
            ArrayList<String> temp = directMessage.get(up);
            temp.add(textMessage);
            directMessage.put(up,temp);
        }
    }

    public void updateFollower(User user, boolean action){
        /*
             if action been true -> means that this user starts to following you
             or don't -> end to following you
             because the client don't chek necessary conditions and only sent one message we should calculate these
        */
        if (action){
            if (!follower.contains(user)){
                follower.add(user);
            }
        }else {
            follower.remove(user);
        }
    }

    private void updateFollowing(User user,boolean action){
        if (action){
            if (!following.contains(user)){
                following.add(user);
            }
        }else {
            following.remove(user);
        }
    }

    @Override
    public boolean equals(Object obj){
        User temp ;
        try {
            temp = (User) obj;
        }catch (Exception e){
            return false;
        }
        if (!this.password.equalsIgnoreCase(temp.password))
            return false;
        return this.username.equalsIgnoreCase(temp.username);
    }

    @Override
    public int hashCode(){
        int result = 17;
        return  result*31*password.hashCode();
    }
}