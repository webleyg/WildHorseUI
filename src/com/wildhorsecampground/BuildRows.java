package com.wildhorsecampground;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuildRows implements GlobalConstants {

    VBox AddProductsToHBox() {
        productsVBox.getChildren().clear();

        HttpRequests httpRequests = new HttpRequests();

        for (int i = 0; i <httpRequests.ProductList().size() ; i++) {
            productsVBox.getChildren().add(BuildProductsHBox(httpRequests.ProductList().get(i)));
//            productsHBox = BuildProductsHBox(httpRequests.ProductList().get(i));
        }
        return productsVBox;
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


    VBox GetOrders(String productName) {

        ordersVBox.getChildren().clear();
        HttpRequests httpRequests = new HttpRequests();

        try {
            for (int i = 0; i < httpRequests.OrdersList(productName).size(); i++) {
                ordersVBox.getChildren().add(BuildOrdersHBox(httpRequests.OrdersList(productName).get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordersVBox;

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
