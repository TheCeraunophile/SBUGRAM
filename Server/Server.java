package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Messages.Requests.User;
import Messages.Requests.*;
import Server.Databace.Databace;

public class Server {
    static Integer numberOfConnectedClients = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Databace.getInstance().init();
        while (true){
                Socket user = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(user.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(user.getInputStream());
                timeOfPush("plus");
                new ClientHandler(oos,ois).start();
        }
    }

    public synchronized static void timeOfPush(String sign){
        Integer integer = Server.numberOfConnectedClients;
        if (sign.equalsIgnoreCase("plus")) {
            try {
                Server.class.getDeclaredField("numberOfConnectedClients").set(integer, integer + 1);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Server.class.getDeclaredField("numberOfConnectedClients").set(integer, integer - 1);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
//        if (Server.numberOfConnectedClients < 1)
//            Databace.getInstance().pushingData();
    }

}
class ClientHandler extends Thread {
    private ObjectInputStream ois ;
    private ObjectOutputStream oos;
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
                    oos.close();
                    ois.close();
                    Server.timeOfPush("minus");
                    break;
                }
                switch (type) {
                    case "Connect" -> {
                        checkDetailsOfClients(message,"connect");
                    }
                    case "CreatingAccount" -> {
                        checkDetailsOfClients(message,"creatingaccount");
                    }
                    case "DelitingAccount" -> {
                        checkDetailsOfClients(message,"deletingAccount");
                    }
                    case "DirectMessage" -> {
                        var a = (DirectMessage) message;
                        Databace.getInstance().cache.get(a.getSender().getUsername()).updateDirectMessage(a.getRisived(),a.getTextMessage());
                        Databace.getInstance().cache.get(a.getRisived().getUsername()).updateDirectMessage(a.getSender(),a.getTextMessage());
                    }
                    case "PostMessage" -> {
                        var a = (PostMessage) message;
                        Databace.getInstance().cache.get(a.getSender().getUsername()).updatePost(a.getText());
                    }
                    case "Refresh" -> {
                        var a = (Refresh) message;
                        sendingUser(Databace.getInstance().cache.get(a.getUsername()));
                        System.out.println("refresh message resived");
                    }
                    case "CompeerMessage" -> {
                        var a = (CompeerMessage) message;
                        Databace.getInstance().cache.get(a.getReceiver().getUsername()).updateFollower(a.getSender(),a.getCompeerType());
                        Databace.getInstance().cache.get(a.getSender().getUsername()).updateFollowing(a.getReceiver(),a.getCompeerType());
                    }
                    case "LikeOrDislikeMessage" -> {
                        var a = (LikeOrDislikeMessage) message;
                        if (a.getGread()==0)
                            Databace.getInstance().cache.get
                                    (a.getResived().getUsername()).getPostList()
                                    .get(Databace.getInstance().cache
                                            .get(a.getResived().getUsername()).getPostList().indexOf(a.getPost()))
                                    .updateDisLike();
                        else
                            Databace.getInstance().cache.get
                                    (a.getResived().getUsername()).getPostList()
                                    .get(Databace.getInstance().cache
                                            .get(a.getResived().getUsername()).getPostList().indexOf(a.getPost()))
                                    .updateLike();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }

    private void sendingUser(User user){
        if (user!=null) {
            try {
                if (user.getClass().getSimpleName().equals("User")) {
                    String security = user.getPassword();
                    user.setPassword(null);
                    this.oos.writeObject(user);
                    this.oos.flush();
                    user.setPassword(security);
                }
                this.oos.writeObject(user);
                this.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                this.oos.writeObject(user);
                this.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkDetailsOfClients(Object user,String status){
        String username=null;
        String password=null;
        String bio = null;

        if (user.getClass().getSimpleName().equalsIgnoreCase("CreatingAccount")){
            var a = (CreatingAccount)user;
            username = a.getUsername();
            password = a.getPassword();
            bio = a.getUsername();
        }
        if (user.getClass().getSimpleName().equalsIgnoreCase("Connect")){
            var a = (Connect)user;
            username = a.getUsername();
            password = a.getPassword();
        }
        if (user.getClass().getSimpleName().equalsIgnoreCase("DelitingAccount")){
            assert user instanceof DelitingAccount;
            var a = (DelitingAccount)user;
            username = a.getUsername();
            password = a.getPassword();
        }
        boolean detailsIsValid = false;
        if (Databace.getInstance().cache.containsKey(username)) {
            if (Databace.getInstance().cache.get(username).getPassword().equals(password)) {
                if (status.equalsIgnoreCase("connect")) {
                    sendingUser(Databace.getInstance().cache.get(username));
                    detailsIsValid=true;
                }
                if (status.equalsIgnoreCase("deletingAccount")){
                    Databace.getInstance().cache.remove(username);
                    sendingUser(new User(null,null,null,null));
                    detailsIsValid=true;
                }
                if (status.equalsIgnoreCase("CreatingAccount")){
                    sendingUser(null); //means one person already used of this username and password OMG
                }
            }else {
                sendingUser(null); //means one person already used of this username or
                                   //hwo want to clear it's account given wrong password or
                                   //hwo want to connect to the server given wrong password
            }
        }
        else {
            if (status.equalsIgnoreCase("CreatingAccount")) {
                User newUser = new User(username, password, null, bio);
                Databace.getInstance().cache.put(username, newUser);
                sendingUser(newUser);
            }else {
                sendingUser(null); //hwo want to clear it's account given wrong username or
                                   //hwo want to connect to the server given wrong username
            }
        }
    }
}