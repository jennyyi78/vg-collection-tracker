package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    LoginModel loginmodel = new LoginModel();
    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginstatus;
    @FXML
    private Button registerButton;
    private static String usernameInput;
    private static Stage userStage;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        if (this.loginmodel.isDatabaseConnected()) {
            this.dbstatus.setText("Connected to the Database");
        } else {
            this.dbstatus.setText("Not Connected to the Database");
        }
    }

    @FXML
    public void Login(ActionEvent event) {
        try {
            if (this.loginmodel.isLogin(this.username.getText(),
                    this.password.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                this.access(this.username.getText());
            } else {
                this.loginstatus.setText("Login Failed!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void access(String username) {
        try {
            setUsername(username);
            userStage = new Stage();
            FXMLLoader loader = new FXMLLoader(
                    this.getClass().getResource("/vg/vg.fxml"));
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            Pane root = loader.load();
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Video Game Collection Tracker");
            userStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void newAccount(ActionEvent event) {
        Stage stage = (Stage) this.registerButton.getScene().getWindow();
        stage.close();
        this.accessRegister();

    }

    public void accessRegister() {
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(this.getClass()
                    .getResource("newAccount.fxml").openStream());
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Video Game Collection Tracker");
            userStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getUsername() {
        return usernameInput;
    }

    public static void setUsername(String user) {
        LoginController.usernameInput = user;
    }

    public static Stage getStage() {
        return userStage;
    }

}
