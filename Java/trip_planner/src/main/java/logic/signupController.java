package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.sql.SQLException;

public class signupController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;

    //control for input data, sign up user
    @FXML
    protected void onClickSignUp(ActionEvent event) throws SQLException, ClassNotFoundException {
        String user = username.getText();
        String passwd = password.getText();
        String passwd2 = password2.getText();
        if(!user.equals("") && !passwd.equals("") && passwd.equals(passwd2)) {
            Operations.signUp(user, passwd, event);
        }
    }

    //redirect to login screen
    @FXML
    protected void openLoginScene(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Operations.openNewScreen(event, "login.fxml");
    }
}
