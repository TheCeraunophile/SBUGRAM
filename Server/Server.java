package Server;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Server.Databace.User;
import Messages.Requests.*;
import Server.Databace.Databace;

public class Server {
    public static void main(String[] args) {
        Databace.getInstance().init();
        while (true){
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                Socket user = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(user.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(user.getInputStream());
                new ClientHandler(oos,ois).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class ClientHandler extends Thread {
    private final ObjectInputStream ois ;
    private final ObjectOutputStream oos;
    public ClientHandler(ObjectOutputStream oos,ObjectInputStream ois){
        this.oos=oos;
        this.ois=ois;
    }
    @Override
    public void run(){
        while (true){
            try {
                Object message = ois.readObject();
                String type = message.getClass().getSimpleName();
                if (type.equals(Disconnect.class.getSimpleName())){
                    var a = (Disconnect)message;
                    oos.close();
                    ois.close();
                    Databace.getInstance().writeDatabace();
                    break;
                }
                switch (type) {
                    case "DirectMessage" -> {
                        var a = (DirectMessage) message;
                        a.getSender().updateDirectMessage(a.getRisived(),a.getTextMessage());
                        a.getRisived().updateDirectMessage(a.getSender(),a.getTextMessage());
                    }
                    case "ConnectedMessage" -> {
                        var a = (Connect) message;

                        /**
                          this sent response of client to the that ,
                          if buoth of username and password are valid if statement sent correct User
                          or Don't sent null
                          */

                        if(
                            Databace.getInstance().cache.containsKey(a.getUsername())
                            &
                            Databace.getInstance().cache.get(a.getUsername()).getPassword().equals(a.getPassword())
                        ){
                            sendingUser(Databace.getInstance().cache.get(a.getUsername()));
                        }else {
                            sendingUser(null);
                        }
                    }
                    case "LikeOrDislikeMessage" -> {
                        var a = (LikeOrDislikeMessage) message;
                        if (a.getGread()==0)
                            a.getResived().getPostList().get(a.getResived().getPostList().indexOf(a.getPost())).updateDisLike();
                        else
                            a.getResived().getPostList().get(a.getResived().getPostList().indexOf(a.getPost())).updateLike();
                    }
                    case "NewUserMessage" -> {
                        var a = (CreatingAccount) message;
                        if(!Databace.getInstance().cache.containsKey(a.getUsername())){
                            User user = new User(a.getUsername(),a.getPassword(),a.getBirthdayDate(),a.getBio());
                            Databace.getInstance().cache.put(a.getUsername(),user);

                        }
                    }
                    case "PostMessage" -> {
                        var a = (PostMessage) message;
                        a.getSender().updatePost(a.getText());
                    }
                    case "Refresh" -> {
                        var a = (Refresh) message;
                        sendingUser(Databace.getInstance().cache.get(a.getUsername()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendingUser(User user){
        try {
            this.oos.writeObject(user);
            this.oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}