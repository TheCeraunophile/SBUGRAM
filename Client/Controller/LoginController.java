package Client.Controller;
import Messages.Requests.User;
import Client.Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
    public Label logOfFailed;
    public Label logOfUsernameFailed;

    /**
     * hiding field not related to login like needhellp and signup
     * */

    public void login(ActionEvent actionEvent){
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
            if (logOfFailed.isVisible())
                logOfFailed.setVisible(false);
            if (logOfUsernameFailed.isVisible())
                logOfUsernameFailed.setVisible(false);
            this.password = password_field.getText();
            username=username_field.getText();
            Connect packet = new Connect(username,password);
                DetailsOfClient.writeObject(packet);
                var answer = DetailsOfClient.readObject();
                if (answer==null){
                    logOfFailed.setVisible(true);
                }else {
                    DetailsOfClient.setProfile((User)answer);
                    DetailsOfClient.setTarget((User) answer);
                    DetailsOfClient.setUsername(username);
                    nextPage();
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

        //username , password and Bio in necessary Not Date
        if (!username_field.getText().equals("") & !password_field.getText().equals("") & !bio_field.getText().equals("")){

            boolean allIsValid = true;

            if (logOfFailed.isVisible())
                logOfFailed.setVisible(false);
            if (logOfUsernameFailed.isVisible())
                logOfUsernameFailed.setVisible(false);

            this.username= username_field.getText();
            this.password=password_field.getText();

            //checking validation of username
            //showed with allIsValid boolean.
            int[] chek = new int[30];
            for (int i=0;i<username.length();i++){
                chek[i] = username.charAt(i);
            }
            for (int s : chek) {
                if (!Character.isAlphabetic((char)s) & !Character.isDigit((char) s) & s!=32 & s!=95 & s!=0){
                    logOfUsernameFailed.setVisible(true);
                    allIsValid=false;
                }
            }

            if (allIsValid) {
                CreatingAccount packet = new CreatingAccount(username, password, null, bio_field.getText());
                DetailsOfClient.writeObject(packet);
                var answer = DetailsOfClient.readObject();
                if (answer == null) {
                    logOfFailed.setVisible(true);
                } else {
                    DetailsOfClient.setProfile((User) answer);
                    DetailsOfClient.setUsername(username);
                    nextPage();
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
        if (logOfFailed.isVisible())
            logOfFailed.setVisible(false);
        if (logOfUsernameFailed.isVisible())
            logOfUsernameFailed.setVisible(false);

        // TODO: 11/06/2021  
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