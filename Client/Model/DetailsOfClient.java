package Client.Model;
import Messages.Requests.CompeerMessage;
import Messages.Requests.CompeerType;
import Messages.Requests.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DetailsOfClient {
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static Socket socket;
    public static User profile;
    private static String username;
    private static User target;

    private DetailsOfClient(){}
    public static void init(){
        try {
            socket = new Socket("127.0.0.1",8080);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUsername(String use) {
        username = use;
    }

    public static void setProfile(User prof) {
        DetailsOfClient.profile = prof;
    }

    public static String getUsername() {
        return username;
    }

    public static User getProfile() {
        return profile;
    }

    public static User getTarget() {
        return target;
    }

    public static void setTarget(User target) {
        DetailsOfClient.target = target;
    }

    public static void closingSrc(){
        try {
            oos.close();
            ois.close();
            socket.close();
            DetailsOfClient.setTarget(null);
            DetailsOfClient.setProfile(null);
            DetailsOfClient.setUsername(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile=null;
        username=null;
        target=null;
    }

    public static void settings(){
        try {
            ArrayList<User> temp = new ArrayList<>(DetailsOfClient.getProfile().getFollowing());
            for (User user : temp) {
                CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(), user.getUsername(), CompeerType.UNFOLLOW,true);
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
            }
            for (User user : temp) {
                String innerUsername = user.getUsername();
                CompeerMessage packet = new CompeerMessage(DetailsOfClient.getProfile(), user.getUsername(), CompeerType.FOLLOW,true);
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
            }
            System.err.println("done ;)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeObject(Object obj){
        try {
            DetailsOfClient.oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(){
        try {
            return DetailsOfClient.ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
