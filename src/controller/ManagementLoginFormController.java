package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-19
 **/

public class ManagementLoginFormController {

    public JFXTextField txtEnterUserName;
    public JFXPasswordField txtEnterUserPassword;
    public AnchorPane root;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String name = txtEnterUserName.getText();
        String password = txtEnterUserPassword.getText();

        if(name.equals("Dulanjaya Sandaruwan") & password.equals("Dulan2001") ){
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/ManagerMainForm.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Manager Main Form");
            primaryStage.centerOnScreen();
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Try Again !").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
