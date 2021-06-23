package Server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import Messages.Requests.User;
import Messages.Requests.*;
import Server.Databace.Databace;

public class Server {
    private static Databace databace;
    static Integer numberOfConnectedClients = 0;
    public static void main(String[] args) throws IOException {
        databace = new Databace();
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
                Socket user = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(user.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(user.getInputStream());
                timeOfPush("plus");
                new ClientHandler(databace,oos,ois).start();
        }
    }

    public synchronized static void timeOfPush(String sign){
        System.out.println("in time of push");
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
        if (Server.numberOfConnectedClients < 1){
            System.out.println("0 shod");
            databace.pushingData();
        }
        // TODO: 20/06/2021 write databace in file system
    }

}
class ClientHandler extends Thread {
    private ObjectInputStream ois ;
    private ObjectOutputStream oos;
    private static Databace databace;
    String ONLINEUSERNAME= "UNKNOWN";
    public ClientHandler(Databace data,ObjectOutputStream oos,ObjectInputStream ois){
        this.oos=oos;
        this.ois=ois;
        databace=data;
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
                    System.out.println("disconnect message received");
                    Server.timeOfPush("minus");
                    break;
                }
                switch (type) {
                    case "Connect" -> {//with its log
                        checkDetailsOfClients(message,"connect");
                    }
                    case "CreatingAccount" -> {//with its log
                        checkDetailsOfClients(message,"creatingaccount");
                    }
                    case "DeletingAccount" -> {//with its log
                        checkDetailsOfClients(message,"deletingAccount");
                    }
                    case "DirectMessage" -> {
                        var a = (DirectMessage) message;
                        databace.getInstance().get(a.getSender().getUsername()).updateDirectMessage(a.getRisived(),a.getTextMessage());
                        databace.getInstance().get(a.getRisived().getUsername()).updateDirectMessage(a.getSender(),a.getTextMessage());
                    }
                    case "PostMessage" -> {//with its log
                        var a = (PostMessage) message;
                        databace.getInstance().get(a.getUsernameSender()).updatePost(a.getText());
                        System.err.println("SERVER SAUD :\n" + a.getUsernameSender() + " SEND NEW POST\nTEXT :" + a.getText());
                    }
                    case "Refresh" -> {//with its log
                        var a = (Refresh) message;
                        sendingUser(databace.getInstance().get(a.getUsername()));
                        System.err.println("SERVER SAID : \n"+a.getUsername()+" GET POSTS LIST");
                    }
                    case "CompeerMessage" -> {//with its log
                        var a = (CompeerMessage) message;
                        databace.getInstance().get(a.getReceiver()).updateFollower(a.getSender(),a.getCompeerType());
                        databace.getInstance().get(a.getSender().getUsername()).updateFollowing(databace.getInstance().get(a.getReceiver()), a.getCompeerType());
                        String type1 = "UNFOLLOWED";
                        if (a.getCompeerType()==CompeerType.FOLLOW)
                            type1="START OF FOLLOW";
                        System.err.println("SERVER SAID :\n" + a.getSender().getUsername() +  type1+" :" + a.getReceiver());

                    }
                    case "ReplyMessage" -> {//whit its log
                        var a = (ReplyMessage)message;
                        Post post = new Post(a.getReplyer(),a.getText());
                        databace.getInstance().get(a.usernameOfReplayed()).getPostList()
                        .
                        get( databace.getInstance().get(a.usernameOfReplayed()).getPostList().indexOf(a.getPost())).addReply(post);
                        databace.getInstance().get(a.usernameOfReplayer()).updatePost(a.getText());
                        System.err.println("SERVER SAID :\n" + a.getReplyer().getUsername() + "SEND REPLY FOR ONE POSTS OF :" + a.getReplyed().getUsername());
                    }
                    case "SearchMessage" -> {//without any log
                        var a = (SearchMessage) message;
                        List<User> kk = new ArrayList<>(databace.getInstance().values());
                        for (int i=kk.size()-1;i>=0;i--){
                            if (!kk.get(i).getUsername().contains(a.getUsernametest()))
                                kk.remove(i);
                        }
                        for (int i=0;i<kk.size();i++) {
                            sendingUser(kk.get(i));
                        }
                        sendingUser(null);
                    }
                    case "LikeOrDislikeMessage" -> {//whit its log
                        var a = (LikeOrDislikeMessage) message;
                        int targetPost = databace.getInstance().get(a.getResived().getUsername()).getPostList().indexOf(a.getPost());
                        try {
                            if (a.getGread()==0) {
                                databace.getInstance().get(a.getResived().getUsername()).getPostList().get(targetPost).updateDisLike();
                                System.err.println("SERVER SAID :\nONE OF " + a.getResived().getUsername() + "'s POSTS DISLIKED");
                            }
                            else {
                                System.out.println("username of post is "+a.getResived().getUsername());
                                System.out.println("text of post :"+a.getPost().getText());
                                databace.getInstance().get(a.getResived().getUsername()).getPostList().get(targetPost).updateLike();
                                System.err.println("SERVER SAID :\nONE OF " + a.getResived().getUsername() + "'s POSTS LIKED");
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("SERVER SAID :\nCONNECTION FAILED :(");
                break;
            }
        }
        System.err.println("SERVER SAID :\nLOGOUT :"+ONLINEUSERNAME);
        ONLINEUSERNAME="UNKNOWN";
    }

    private void sendingUser(User user){
        if (user!=null) {
            try {
                this.oos.writeObject(user);
                this.oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                this.oos.writeObject(null);
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
        if (user.getClass().getSimpleName().equalsIgnoreCase("DeletingAccount")){
            assert user instanceof DeletingAccount;
            var a = (DeletingAccount)user;
            username = a.getUsername();
            password = a.getPassword();
        }
        if (databace.getInstance().containsKey(username)) {
            if (databace.getInstance().get(username).getPassword().equals(password)) {
                if (status.equalsIgnoreCase("connect")) {
                    sendingUser(databace.getInstance().get(username));
                    System.err.println("SERVER SAID :\nACTION : LOGIN \nUSERNAME:" + username + "\nPASSWORD :" + password);
                    ONLINEUSERNAME = username;
                }
                if (status.equalsIgnoreCase("deletingAccount")){
                    databace.getInstance().remove(username);
                    sendingUser(new User(null,null,null,null));
                    System.err.println("SERVER SAID :\nDELETE ACCOUNT : LOGIN \nUSERNAME:" + username + "\nPASSWORD :" + password);
                    ONLINEUSERNAME = "UNKNOWN";
                }
                if (status.equalsIgnoreCase("CreatingAccount")){
                    sendingUser(null); //means one person already used of this username and password OMG
                    System.err.println("SERVER SAID : \nONE INVALID ATTEMPT FOR CONNECTING DROOPED");
                }
            }else {
                sendingUser(null); //means one person already used of this username or
                                   //hwo want to clear it's account given wrong password or
                                   //hwo want to connect to the server given wrong password
                System.err.println("SERVER SAID : \nONE INVALID ATTEMPT FOR CONNECTING DROOPED");
            }
        }
        else {
            if (status.equalsIgnoreCase("CreatingAccount")) {
                User newUser = new User(username, password, null, bio);
                databace.getInstance().put(username, newUser);
                sendingUser(newUser);
                System.err.println("SERVER SAID :\nACTION : SIGNUP \nUSERNAME:" + username + "\nPASSWORD :" + password);
                ONLINEUSERNAME = username;
            }else {
                sendingUser(null); //hwo want to clear it's account given wrong username or
                                   //hwo want to connect to the server given wrong username
                System.err.println("SERVER SAID : \nONE INVALID ATTEMPT FOR CONNECTING DROOPED");
            }
        }
    }
}