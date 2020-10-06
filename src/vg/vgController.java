package vg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import login.LoginController;

public class vgController implements Initializable {

    @FXML
    private TextField gametitle;
    @FXML
    private TextField rating;
    @FXML
    private TextField hoursplayed;
    @FXML
    private TextField platform;
    @FXML
    private Label invalidValues;
    @FXML
    private Label filename;

    @FXML
    private TableView<vgData> vgTable;
    @FXML
    private TableColumn<vgData, ImageView> imageColumn;
    @FXML
    private TableColumn<vgData, String> gameColumn;
    @FXML
    private TableColumn<vgData, Integer> ratingColumn;
    @FXML
    private TableColumn<vgData, Integer> hoursColumn;
    @FXML
    private TableColumn<vgData, String> platformColumn;

    private dbConnection db;
    private ObservableList<vgData> data;
    private byte[] imgBytes;

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        this.db = new dbConnection();
        this.refresh();
    }

    @FXML
    private void refresh() {
        String sql = "Select * FROM " + LoginController.getUsername()
                + "Videogames";
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet result = conn.createStatement().executeQuery(sql);
            while (result.next()) {
                this.data.add(new vgData(result.getBytes(1),
                        result.getString(2), result.getInt(3), result.getInt(4),
                        result.getString(5)));
            }
        } catch (SQLException ex) {
            System.err.println("Error " + ex);
        }

        this.imageColumn.setCellValueFactory(
                new PropertyValueFactory<vgData, ImageView>("image"));
        this.gameColumn.setCellValueFactory(
                new PropertyValueFactory<vgData, String>("gameTitle"));
        this.ratingColumn.setCellValueFactory(
                new PropertyValueFactory<vgData, Integer>("rating"));
        this.hoursColumn.setCellValueFactory(
                new PropertyValueFactory<vgData, Integer>("hoursPlayed"));
        this.platformColumn.setCellValueFactory(
                new PropertyValueFactory<vgData, String>("platform"));

        this.vgTable.setItems(null);
        this.vgTable.setItems(this.data);
    }

    @FXML
    private void addGame(ActionEvent event) {
        String sqlInsert = "INSERT INTO " + LoginController.getUsername()
                + "Videogames(Thumbnail,game,Rating,hours,Platform) VALUES (?,?,?,?,?)";
        try {

            if (!this.gametitle.getText().trim().isEmpty()
                    && isInteger(this.rating.getText())
                    && isInteger(this.hoursplayed.getText())
                    && !this.platform.getText().trim().isEmpty()) {
                Connection conn = dbConnection.getConnection();
                PreparedStatement state = conn.prepareStatement(sqlInsert);

                state.setBytes(1, this.imgBytes);
                state.setString(2, this.gametitle.getText());
                state.setInt(3, Integer.valueOf(this.rating.getText()));
                state.setInt(4, Integer.valueOf(this.hoursplayed.getText()));
                state.setString(5, this.platform.getText());

                state.execute();
                state.close();
                this.clear();
                this.refresh();
            } else {
                this.invalidValues.setText("Invalid Values");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void deleteGame(ActionEvent event) {
        vgData selectedItem = this.vgTable.getSelectionModel()
                .getSelectedItem();
        this.vgTable.getItems().remove(selectedItem);
        if (selectedItem != null) {
            String sqlInsert = "DELETE FROM " + LoginController.getUsername()
                    + "Videogames WHERE game=?";
            try {
                Connection conn = dbConnection.getConnection();
                PreparedStatement state = conn.prepareStatement(sqlInsert);
                state.setString(1, selectedItem.getGameTitle());
                state.execute();
                state.close();
            } catch (SQLException ex) {
                System.err.println("Error " + ex);
            }

        }
        this.refresh();
    }

    @FXML
    private void uploadThumbnail(ActionEvent event) {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser
                .showOpenDialog(LoginController.getStage());
        if (selectedFile != null) {
            try {
                this.filename.setText(selectedFile.getName());
                this.imgBytes = Files.readAllBytes(selectedFile.toPath());

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        this.clear();
    }

    private void clear() {
        this.gametitle.setText("");
        this.rating.setText("");
        this.hoursplayed.setText("");
        this.platform.setText("");
        this.invalidValues.setText("");
        this.filename.setText("");
    }

    private static boolean isInteger(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
