package login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import dbUtil.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class registerController implements Initializable {

    LoginModel loginmodel = new LoginModel();
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label invalidUsername;
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;
    private dbConnection db;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        this.db = new dbConnection();
    }

    public void previousScreen(ActionEvent event) {
        try {
            Stage stage = (Stage) this.backButton.getScene().getWindow();
            stage.close();

            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(
                    this.getClass().getResource("login.fxml").openStream());
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Video Game Collection Tracker");
            userStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void createAccount(ActionEvent event) {
        String usernameInput = this.username.getText();
        String passwordInput = this.password.getText();
        try {
            if (this.loginmodel.alreadyCreated(usernameInput)) {
                this.invalidUsername.setText("Username is Already Taken");
            } else {
                String sqlInsert = "INSERT INTO login(username,password) VALUES (?,?)";
                Connection conn = dbConnection.getConnection();
                PreparedStatement state = conn.prepareStatement(sqlInsert);

                state.setString(1, usernameInput);
                state.setString(2, passwordInput);

                this.createTable(conn, usernameInput);

                state.execute();
                state.close();

                this.invalidUsername.setText("Account was Registered");

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void createTable(Connection conn, String username) {
        try {
            String sqlTable = "CREATE TABLE " + username
                    + "Videogames(Thumbnail BLOB, game TEXT, Rating INTEGER unsigned DEFAULT NULL, hours INTEGER unsigned DEFAULT NULL, Platform TEXT)";
            PreparedStatement statement = conn.prepareStatement(sqlTable);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
