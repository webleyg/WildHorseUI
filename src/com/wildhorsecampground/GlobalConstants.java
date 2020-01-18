package com.wildhorsecampground;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface GlobalConstants {

    StackPane stackPane = new StackPane();
    VBox productsVBox = new VBox();
    VBox ordersVBox = new VBox();
    Stage stage = new Stage();
    GridPane mainGridPane = new GridPane();
    MenuItem refundMenuItem = new MenuItem("Refund");
    MenuItem orderDetailsMenuItem = new MenuItem("Order Details");
    ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();


}
