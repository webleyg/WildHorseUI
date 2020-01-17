package com.wildhorsecampground;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

class BuildRows implements GlobalConstants {

    void AddProductsToHBox() {
        productsVBox.getChildren().clear();

        HttpRequests httpRequests = new HttpRequests();
        ArrayList<String> tempList = httpRequests.ProductList();

        tempList.forEach(item -> productsVBox.getChildren().add(BuildProductsHBox(item)));

    }

    void AddOrdersToHBox(String productName) throws Exception {
        HttpRequests httpRequests = new HttpRequests();
        ordersVBox.getChildren().clear();
        Integer productID = httpRequests.GetProductID(productName);

        ArrayList<String> tempList = httpRequests.OrdersList(productID);

        tempList.forEach(item -> ordersVBox.getChildren().add(BuildOrdersHBox(item)));

    }

    private HBox BuildProductsHBox(String productLabelText) {

        HBox rowHBox = new HBox();
        rowHBox.setPickOnBounds(false);
        CheckBox checkBox = new CheckBox();
        checkBox.setPickOnBounds(false);
        Label productLabel = new Label(productLabelText);
        productLabel.setPickOnBounds(false);
        rowHBox.getChildren().addAll(checkBox, productLabel);

        return rowHBox;
    }

    private HBox BuildOrdersHBox(String label) {

        HBox rowHBox = new HBox();
        rowHBox.setPickOnBounds(false);
        Label nameLabel = new Label(label);
        nameLabel.setPickOnBounds(false);

        rowHBox.getChildren().addAll(nameLabel);

        return rowHBox;

    }

}
