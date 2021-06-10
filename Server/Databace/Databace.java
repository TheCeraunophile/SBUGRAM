package Server.Databace;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class Databace {
    private  ObjectOutputStream oos;
    private  ObjectInputStream ois;
    public ConcurrentHashMap<String, User> cache = null;
    private static final Databace databace = new Databace();

    public void init(){
        try {
            File repository = new File("/lunatic/home/Documents/Repository");
            FileOutputStream fos = new FileOutputStream(repository);
            FileInputStream fis = new FileInputStream(repository);
            oos = new ObjectOutputStream(fos);
            ois = new ObjectInputStream(fis);
            cache = (ConcurrentHashMap<String,User>) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("no one joined to SBU Gram :/");
            cache = new ConcurrentHashMap<>();
        }
    }

    public void pushingData(){
        try {
            oos.writeObject(cache);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Databace (){}

    public static Databace getInstance(){
        return databace;
    }
}