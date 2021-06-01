package Controller;

import Model.PageLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    public TextField username_field;
    public Button login_button;
    public Button Sign_up_button;
    public Button need_help_button;
    public TextField year_test;
    public TextField month_test;
    public TextField day_test;
    public Button save_test_button;
    public TextField bio_test;
    public TextField password_field;
    public TextField name_hellp;
    public TextField broken_pass;
    public TextField day_birthday;
    public CheckBox showPasswordButton;

    public void creating_new_account(){
        bio_test.setVisible(true);
        save_test_button.setVisible(true);
        day_test.setVisible(true);
        month_test.setVisible(true);
        year_test.setVisible(true);
        username_field.setVisible(true);
        password_field.setVisible(true);
        name_hellp.setVisible(false);
        day_birthday.setVisible(false);
        broken_pass.setVisible(false);
        showPasswordButton.setVisible(true);
    }
    public void fill_usernameAndPassword(){
        bio_test.setVisible(false);
        save_test_button.setVisible(false);
        day_test.setVisible(false);
        month_test.setVisible(false);
        year_test.setVisible(false);
        username_field.setVisible(true);
        password_field.setVisible(true);
        name_hellp.setVisible(false);
        day_birthday.setVisible(false);
        broken_pass.setVisible(false);
        showPasswordButton.setVisible(true);

    }
    public void need_hellp(){
        fill_usernameAndPassword();
        username_field.setVisible(false);
        password_field.setVisible(false);
        name_hellp.setVisible(true);
        day_birthday.setVisible(true);
        broken_pass.setVisible(true);
        showPasswordButton.setVisible(false);
    }
    public void showPassword(){
        try {
            new PageLoader().load("TimeLine");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
