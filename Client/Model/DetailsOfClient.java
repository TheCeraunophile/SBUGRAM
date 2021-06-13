package Client.Model;
import Messages.Requests.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        profile = prof;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        profile=null;
        username=null;
    }
}
