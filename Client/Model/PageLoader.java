package Client.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PageLoader {
    private static final int height = 600;
    private static final int width = 900;
    private static Stage stage ;
    private static Scene scene;

    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public static void initStage(Stage primaryStage){
        stage=primaryStage;
        primaryStage.setTitle("SBUGram");
        stage.setWidth(width);
        stage.setHeight(height);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Client/View/" +fxml+".fxml"));
        return fxmlLoader.load();
    }
    
    public void load(String uri) throws IOException {
        scene = new Scene(new PageLoader().loadFXML(uri));
        stage.setScene(scene);
        stage.show();
    }

    public void load(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Client/View/" + fxml + ".fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }
}
