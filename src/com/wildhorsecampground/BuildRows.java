package com.wildhorsecampground;

import javafx.collections.ListChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
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
        ArrayList<String> nameArrayList = new ArrayList<>();
        ArrayList<String> rowHBoxArrayList = new ArrayList<>();
        ordersVBox.getChildren().clear();
        Integer productID = httpRequests.GetProductID(productName);

        JSONArray tempList = httpRequests.OrdersList(productID);

        // Get First and Last name in Pretty Format

        for (int i = 0; i < tempList.length(); i++) {

            JSONObject billing = (JSONObject) ((JSONObject) tempList.get(i)).get("billing");
                nameArrayList.add(billing.get("first_name").toString().toLowerCase().substring(0, 1).toUpperCase() + billing.get("first_name").toString().substring(1) +
                        " " + billing.get("last_name").toString().toLowerCase().substring(0,1).toUpperCase() + billing.get("last_name").toString().substring(1));

            rowHBoxArrayList.add(((JSONObject) tempList.get(i)).get("id").toString());


        }

        for (int i = 0; i < nameArrayList.size(); i++) {
            ordersVBox.getChildren().add(BuildOrdersHBox(nameArrayList.get(i), rowHBoxArrayList.get(i)));
        }

//        tempList.forEach(item -> ordersVBox.getChildren().add(BuildOrdersHBox(item)));

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

    private HBox BuildOrdersHBox(String label, String rowHBoxID) {

        MultipleCheckBoxController multipleCheckBoxController = new MultipleCheckBoxController();

        HBox rowHBox = new HBox();
        rowHBox.setId(rowHBoxID);
        rowHBox.setPickOnBounds(false);
        CheckBox checkBox = new CheckBox();

        multipleCheckBoxController.configureCheckBox(checkBox);

        Label nameLabel = new Label(label);
        nameLabel.setPickOnBounds(false);

        rowHBox.getChildren().addAll(checkBox, nameLabel);

        return rowHBox;

    }

}
