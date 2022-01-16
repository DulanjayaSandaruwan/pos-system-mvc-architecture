package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-18
 **/
public class PlaceOrderFormController {
    public AnchorPane root1;
    public Label lblTime;
    public Label lblDate;
    public ComboBox <String>cmbCustomerIds;
    public ComboBox <String>cmbItemCodes;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerProvince;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtCustomerPostalCode;
    public TableView <CartTM> tblCart;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public JFXTextField txtQty;
    public TextField txtDiscount;
    public Label lblTotal;
    public Label lblOrderId;

    int cartSelectedRowForRemove = -1;

    public void initialize(){

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAndTime();
        setOrderId();
        try {
            loadCustomerIds();
            loadItemCodes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        cmbCustomerIds.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) ->{
                    try {
                        setCustomerData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });

        cmbItemCodes.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setItemData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

    }

    private void setOrderId() {
        try {
            lblOrderId.setText(new OrderDetailsController().getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setItemData(String itemCode) throws SQLException {
        Item t1 = new ItemController().searchItem(itemCode);
        if(t1 == null){
            new Alert(Alert.AlertType.WARNING, "Empty Result Set !");
        }
        else {
            txtDescription.setText(t1.getDescription());
            txtUnitPrice.setText(String.valueOf(t1.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(t1.getQtyOnHand()));
        }
    }

    private void setCustomerData(String customerId) throws SQLException {
        Customer c1 = new CustomerController().searchCustomer(customerId);
        if(c1 == null){
            new Alert(Alert.AlertType.WARNING,"Empty Result Set !");
        }
        else {
            txtCustomerName.setText(c1.getName());
            txtCustomerAddress.setText(c1.getAddress());
            txtCustomerProvince.setText(c1.getProvince());
            txtCustomerPostalCode.setText(c1.getPostalCode());

        }
    }

    private void loadItemCodes() throws SQLException {
        List<String> itemCodes = new ItemController().getItemCodes();
        cmbItemCodes.getItems().addAll(itemCodes);
    }

    private void loadCustomerIds() throws SQLException {
        List<String> customerIds = new CustomerController().getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }) ,
            new KeyFrame(Duration.seconds(1))
        );

        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void btnManageCustomersOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) root1.getScene().getWindow();
        stage1.close();
    }

    public void btnManagementLoginOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManagementLoginForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) root1.getScene().getWindow();
        stage1.close();
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        double total = (qtyForCustomer * unitPrice);
        double finalTotal = total - ((total/100)*discount);

        if(qtyOnHand < qtyForCustomer){
            new Alert(Alert.AlertType.WARNING, "Invalid Qty !").showAndWait();
            return;
        }

        CartTM tm = new CartTM(
                cmbItemCodes.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                discount,
                finalTotal
        );

        int rowNumber = isExists(tm);

        if(rowNumber == -1){
            obList.add(tm);
        }
        else {
            CartTM tempTm = obList.get(rowNumber);
            CartTM newTm = new CartTM(
              tempTm.getCode(),
              tempTm.getDescription(),
              tempTm.getQty()+qtyForCustomer,
              unitPrice,
              discount,
              total+tempTm.getTotal()
            );


            obList.remove(rowNumber);
            obList.add(newTm);
        }

        tblCart.setItems(obList);
        calculateCost();

    }

    private int isExists(CartTM tm){

        for (int i=0; i<obList.size();i++){
            if(tm.getCode().equals(obList.get(i).getCode())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl =0;

        for (CartTM tm:obList
             ) {
            ttl +=tm.getTotal();
        }
        lblTotal.setText(ttl+" /=");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if(cartSelectedRowForRemove == -1){
            new Alert(Alert.AlertType.WARNING, "Please Select A Row !").show();
        }
        else {
            obList.remove(cartSelectedRowForRemove);
            tblCart.refresh();
        }
    }

    public void btnPlaceOrderFormController(ActionEvent actionEvent) {
        ArrayList<OrderDetails> orders = new ArrayList<>();
        for (CartTM tempTm:obList
             ) {

            orders.add(new OrderDetails(
                    tempTm.getCode(),
                    tempTm.getQty(),
                    tempTm.getDiscount()
            ));
        }

        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbCustomerIds.getValue(),
                orders
        );

        if(new OrderDetailsController().placeOrder(order)){
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Added !").show();
            setOrderId();
        }
        else {
            new Alert(Alert.AlertType.WARNING, "Try Again !").show();
        }

    }

    public void btnManageOrdersOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ManageOrderDetailsForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) root1.getScene().getWindow();
        stage1.close();
    }

    public void btnMakePaymentOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/MakePaymentOnActionForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
