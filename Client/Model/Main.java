package Client.Model;

import Messages.Requests.Disconnect;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        new PageLoader().load("Login");
    }

    public static void main(String[] args) {
        DetailsOfClient.init();
        launch(args);

        if (DetailsOfClient.getUsername()!=null) {
            Disconnect disconnect = new Disconnect(DetailsOfClient.getUsername());
            try {
                DetailsOfClient.oos.writeObject(disconnect);
                DetailsOfClient.oos.flush();
                DetailsOfClient.closingSrc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Disconnect disconnect = new Disconnect(null);
            try {
                DetailsOfClient.oos.writeObject(disconnect);
                DetailsOfClient.oos.flush();
                DetailsOfClient.closingSrc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }
}
