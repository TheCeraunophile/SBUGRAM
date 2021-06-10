package Server.Databace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class User implements Serializable {
    private String username;
    private String password;
    private Date birthdayDate;
    private String bio;
    private ArrayList<Post> postList;
    private ArrayList<User> followers;
    private ArrayList<User> followed;
    private HashMap<User,ArrayList<String>> directMessage;

    public void updateFollowed(User user,String action){
        // TODO: 27/05/2021
    }

    public void updatePost(String text){
        Post post = new Post(this,text);
        postList.add(post);
    }

    private void updateFollwers(User user,String action){
        // TODO: 26/05/2021
    }

    public ArrayList<Post> getPostList (){
        return postList;
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

    public User(String username,String password,Date birthdayDate,String bio){
        this.username=username;
        this.password=password;
        this.birthdayDate=birthdayDate;
        this.bio=bio;
    }

    @Override
    public boolean equals(Object obj){
        User temp ;
        try {
            temp = (User) obj;
        }catch (Exception e){
            return false;
        }
        if (!this.password.equals(temp.password))
            return false;
        if (!this.username.equals(temp.username))
            return false;
        return true;
    }

    @Override
    public int hashCode(){
        int result = 17;
        return  result*31*password.hashCode();
    }
}