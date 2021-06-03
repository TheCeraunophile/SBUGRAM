module TextGram {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    opens Client.View;
    opens Client.Model;
    opens Client.Controller;
}