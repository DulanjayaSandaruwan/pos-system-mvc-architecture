package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.OrderDetails;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-21
 **/
public class UpdateOrderDetailsFormController {

    public JFXTextField txtOrderId;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public AnchorPane root;
    public JFXTextField txtItemCode;

    public void txtSelectOrderIdOnAction(ActionEvent actionEvent) throws SQLException {
        String orderId = txtOrderId.getText();

        OrderDetails o1= new OrderDetailsController().searchOrder(orderId);
        if (o1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(o1);
        }
    }

    private void setData(OrderDetails o) {
        txtItemCode.setText(o.getItemCode());
        txtQty.setText(String.valueOf(o.getOrderQty()));
        txtDiscount.setText(String.valueOf(o.getDiscount()));

    }

    public void btbUpdateOrderDetailsOnAction(ActionEvent actionEvent) throws SQLException {
        OrderDetails o1= new OrderDetails(
                txtItemCode.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtDiscount.getText())
        );

        if (new OrderDetailsController().updateOrderDetails(o1,txtOrderId.getText()))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated !").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageOrderDetailsForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
