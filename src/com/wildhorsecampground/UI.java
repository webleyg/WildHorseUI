package com.wildhorsecampground;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;


class UI implements GlobalConstants {

    private MenuItem listProductsMenuItem = new MenuItem("List Products");
    private MenuBar topMenuBar = new MenuBar();


    void buildUI() {

        BuildRows buildRows = new BuildRows();

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setWidth(1500);
        stage.setHeight(600);
        stage.setTitle("Wildhorse Campground");

//        topMenuBar = new MenuBar();
        topMenuBar.setId("Top Menu Bar");
        Menu fileMenu = new Menu("File");
        MenuItem testMenuItem = new MenuItem("Test");

        fileMenu.getItems().add(listProductsMenuItem);
        fileMenu.getItems().add(testMenuItem);

        topMenuBar.getMenus().add(fileMenu);
        topMenuBar.setPrefWidth(scene.getWidth());

        mainGridPane.add(topMenuBar, 0, 0);
        mainGridPane.setVgap(6);
        mainGridPane.setHgap(6);
        productsVBox.setSpacing(6);


        stackPane.getChildren().add(mainGridPane);

        // Height Listener
        stackPane.heightProperty().addListener(observable -> {
            mainGridPane.setPrefHeight(stackPane.getHeight());
        });

        // Width Listener
        stackPane.widthProperty().addListener(observable -> {
            mainGridPane.setPrefWidth(stackPane.getWidth());
        });

        stage.widthProperty().addListener(observable -> {
            topMenuBar.setPrefWidth(stage.getWidth());
        });


        stage.show();

        /////////BUTTON CODE//////////

        listProductsMenuItem.setOnAction(event -> ViewProducts());

        testMenuItem.setOnAction(event -> {


            HttpRequests httpRequests = new HttpRequests();

            try {
                httpRequests.OrdersList(1685);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void ResetInterface() {
        mainGridPane.getChildren().clear();
        productsVBox.getChildren().clear();
        ordersVBox.getChildren().clear();


    }

    private void ViewProducts() {

        BuildRows buildRows = new BuildRows();
        mainGridPane.getChildren().clear();

        mainGridPane.add(topMenuBar, 0, 0);
        Button submitButton = new Button("View Orders");

        submitButton.setOnAction(event -> {
            SubmitProducts();
        });

        mainGridPane.add(submitButton, 0, 2);

        buildRows.AddProductsToHBox();
        mainGridPane.add(productsVBox, 0, 1);

    }

    private void ViewOrders() {
        BuildRows buildRows = new BuildRows();

        mainGridPane.getChildren().clear();
        mainGridPane.add(topMenuBar, 0, 0);
        mainGridPane.getChildren().remove(productsVBox);
        buildRows.mainGridPane.add(ordersVBox, 0, 1);

    }

    private void SubmitProducts() {

        BuildRows buildRows = new BuildRows();

        productsVBox.getChildren().forEach(hbox -> {
            ((HBox) hbox).getChildren().forEach(node -> {
                if (node instanceof CheckBox) {
                    if ((((CheckBox) node).isSelected())) {

                        Parent parent = node.getParent();
                        ((HBox) parent).getChildren().forEach(node1 -> {
                            if (node1 instanceof Label) {
                                if (((Label) node1).getText() != null) {
                                    try {
                                        buildRows.AddOrdersToHBox(((Label) node1).getText());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        System.out.println("No checkbox selected");
                    }
                }
            });
        });
        ViewOrders();
    }
}
