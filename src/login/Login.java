package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = (Parent) FXMLLoader
                .load(this.getClass().getResource("login.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Game Collection Tracker");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
