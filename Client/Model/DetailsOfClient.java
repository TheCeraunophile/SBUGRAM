package Client.Model;
import Server.Databace.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DetailsOfClient {
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static Socket socket;
    private static final String defultName = "newUser";
    public static User profile;
    private static String username;
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

    public static void setUsername(String username) {
        DetailsOfClient.username = username;
    }

    public static void setProfile(User profile) {
        DetailsOfClient.profile = profile;
    }

    public static String getUsername() {
        return username;
    }

    public static User getProfile() {
        return profile;
    }
}
