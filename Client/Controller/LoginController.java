package Client.Controller;

import Client.Model.PageLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import Messages.Requests.*;
import java.io.IOException;
import java.util.Date;

public class LoginController {
    private String password;
    private String username;
    public TextField username_field;
    public Button login_button;
    public Button Signup_button;
    public Button needhelp_button;
    public TextField year_test;
    public TextField month_test;
    public TextField day_test;
    public TextField bio_field;
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

        if (!username_field.getText().equals("") & !password_field.getText().equals("")){
            this.password=password_field.getText();
            this.username_field.getText();
            Connect packet = new Connect(username,password);
//            oos.writeobject(packet);
//            ois.readObject();
            nextPage();
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
            this.password=password_field.getText();
            this.username_field.getText();
            CreatingAccount packet = new CreatingAccount(username,password,null,bio_field.getText());
//            oos.writeobject(packet);
//            ois.readObject();
            nextPage();
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

    public void save_signup(){

    }

    public void showPassword(){

    }
    public void nextPage(){
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}