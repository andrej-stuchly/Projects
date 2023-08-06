package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;


public class loginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    //login control for password, redirect to main part
    @FXML
    protected void onClickLogIn(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String user = username.getText();
        String passwd = password.getText();
        if(!user.equals("") && !passwd.equals("")) {
            Operations.logIn(user, passwd, event);
            if (Operations.user == null) {
                return;
            }
            Operations.openNewScreen(event, "project.fxml");
        }
    }

    //redirect to signup
    @FXML
    protected void openSignUpScene(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Operations.openNewScreen(event, "signup.fxml");
    }
}
