package Messages.Requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String username;
    private String password;
    private String location;
    private String bio;
    private  ArrayList<Post> postList = new ArrayList<>();
    private  ArrayList<User> following = new ArrayList<>();
    private  ArrayList<User> follower = new ArrayList<>();
    private  HashMap<User,ArrayList<String>> directMessage = new HashMap<>();

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
//        postList = new ArrayList<>(postList);
    }

    public String getBio() {
        return bio;
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

    public void updateFollower(User user, CompeerType type){
        if (type==CompeerType.UNFOLLOW){
            follower.remove(user);
        }else {
            follower.add(user);
        }
    }

    public void updateFollowing(User user,CompeerType type){
        if (type==CompeerType.FOLLOW){
            if (!following.contains(user)){
                following.add(user);
            }
        }else {
            following.remove(user);
        }
    }

    public ArrayList<User> getFollower() {
        return follower;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    @Override
    public boolean equals(Object obj){
        if (obj==null) {
            return false;
        }
        User temp ;
        try {
            temp = (User) obj;
        }catch (Exception e){
            return false;
        }
        return this.username.equalsIgnoreCase(temp.username);
    }

    @Override
    public int hashCode(){
        int result = 17;
        return  result*31*username.hashCode();
    }
}