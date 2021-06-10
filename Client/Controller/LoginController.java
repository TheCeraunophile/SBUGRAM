package Client.Controller;
import Server.Databace.User;
import Client.Model.PageLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Messages.Requests.*;
import java.io.IOException;
import Client.Model.DetailsOfClient;

public class LoginController{

    private String password;
    private String username;

    public TextField show_password;
    public TextField username_field;
    public Button login_button;
    public Button Signup_button;
    public Button needhelp_button;
    public TextField year_test;
    public TextField month_test;
    public TextField day_test;
    public TextArea bio_field;
    public TextField password_field;
    public TextField name_hellp;
    public TextField broken_pass;
    public TextField day_birthday;
    public CheckBox showPasswordButton;

    /**
     * hiding field not related to login like needhellp and signup
     * */

    public void login(){
        username_field.setVisible(true);
        password_field.setVisible(true);
        showPasswordButton.setVisible(true);

        broken_pass.setVisible(false);
        name_hellp.setVisible(false);
        day_birthday.setVisible(false);

        bio_field.setVisible(false);
        day_test.setVisible(false);
        month_test.setVisible(false);
        year_test.setVisible(false);

        boolean connectionIsValid = false;
        if (!username_field.getText().equals("") & !password_field.getText().equals("")){
            this.password = password_field.getText();
            username=username_field.getText();
            Connect packet = new Connect(username,password);
            try {
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
                var answer = DetailsOfClient.ois.readObject();
                if (answer==null){
                    System.out.println("this username not available :/");
                }else {
                    DetailsOfClient.setProfile((User)answer);
                    DetailsOfClient.setUsername(username);
                    connectionIsValid=true;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            if (connectionIsValid) {
                nextPage();
            }else {
                try {
                    password_field.setText("");
                    username_field.setText("");
                    day_birthday.setText("");
                    year_test.setText("");
                    month_test.setText("");
                    show_password.setText("");
                    bio_field.setText("");
                    new PageLoader().load("Login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void signup(){

        username_field.setVisible(true);
        password_field.setVisible(true);
        showPasswordButton.setVisible(true);

        bio_field.setVisible(true);
        day_test.setVisible(true);
        month_test.setVisible(true);
        year_test.setVisible(true);

        broken_pass.setVisible(false);
        name_hellp.setVisible(false);
        day_birthday.setVisible(false);

        if (
                !username_field.getText().equals("")
                &
                !password_field.getText().equals("")
                &
                !bio_field.getText().equals("")
        ){
            boolean connectionIsValid = false;
            this.password=password_field.getText();
            this.username= username_field.getText();

            CreatingAccount packet = new CreatingAccount(username,password,null,bio_field.getText());
            try {
                DetailsOfClient.oos.writeObject(packet);
                DetailsOfClient.oos.flush();
                var answer = DetailsOfClient.ois.readObject();
                if (answer==null){
                    System.out.println("one username already exist");
                }else {
                    DetailsOfClient.setProfile((User)answer);
                    DetailsOfClient.setUsername(username);
                    connectionIsValid=true;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (connectionIsValid) {
                nextPage();
            }else {
                try {
                    password_field.setText("");
                    username_field.setText("");
                    day_birthday.setText("");
                    year_test.setText("");
                    month_test.setText("");
                    show_password.setText("");
                    bio_field.setText("");
                    new PageLoader().load("Login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void needhellp(){
        username_field.setVisible(false);
        password_field.setVisible(false);
        showPasswordButton.setVisible(false);

        bio_field.setVisible(false);
        day_test.setVisible(false);
        month_test.setVisible(false);
        year_test.setVisible(false);

        broken_pass.setVisible(true);
        name_hellp.setVisible(true);
        day_birthday.setVisible(true);
    }

    public void showPassword(){
        if (password_field.isVisible()){
            password_field.setVisible(false);
            show_password.setVisible(true);
            show_password.setText(password_field.getText());
        }else {
            show_password.setVisible(false);
            password_field.setVisible(true);
            password_field.setText(show_password.getText());
        }
    }
    public void nextPage(){
        try {
            password_field.setText("");
            username_field.setText("");
            day_birthday.setText("");
            year_test.setText("");
            month_test.setText("");
            show_password.setText("");
            bio_field.setText("");
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}