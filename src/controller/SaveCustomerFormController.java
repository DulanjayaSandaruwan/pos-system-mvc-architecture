package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-18
 **/

public class SaveCustomerFormController {
    public AnchorPane root;

    public JFXTextField txtCustomerID;
    public JFXTextField txtCustomerTitle;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerCity;
    public JFXTextField txtCustomerProvince;
    public JFXTextField txtCustomerPostalCode;
    public JFXButton btnSaveCustomer;

    public void btnSaveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Customer c1 = new Customer(
                txtCustomerID.getText(),
                txtCustomerTitle.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtCustomerCity.getText(),
                txtCustomerProvince.getText(),
                txtCustomerPostalCode.getText()
        );

        if(new CustomerController().saveCustomer(c1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added !").showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManageCustomerForm.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage1 = (Stage) root.getScene().getWindow();
            Stage stage2 = (Stage) root.getScene().getWindow();
            stage1.close();
            stage2.close();
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.show();
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Try Again !").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
