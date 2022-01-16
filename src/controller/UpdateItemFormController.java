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
import model.Customer;
import model.Item;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-20
 **/
public class UpdateItemFormController {
    public AnchorPane root;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnUpdateItem;

    public void txtSelectItemOnAction(ActionEvent actionEvent) throws SQLException {
        String code = txtItemCode.getText();

        Item t1 = new ItemController().searchItem(code);
        if (t1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(t1);
        }
    }

    public void btnUpdateItemOnAction(ActionEvent actionEvent) throws SQLException {
        Item t1 = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if (new ItemController().updateItem(t1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated !").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    void setData(Item t){
        txtItemCode.setText(t.getCode());
        txtDescription.setText(t.getDescription());
        txtPackSize.setText(t.getPackSize());
        txtUnitPrice.setText(String.valueOf(t.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(t.getQtyOnHand()));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageItemFom.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
