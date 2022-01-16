package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-19
 **/
public class SaveItemFormController {

    public AnchorPane root;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSaveItem;

    public void btnSaveItemOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        Item t1 = new Item(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    txtPackSize.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())

        );

        if(new ItemController().saveItem(t1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added !").showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManageItemFom.fxml"));
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
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageItemFom.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
