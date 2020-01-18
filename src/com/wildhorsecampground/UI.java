package com.wildhorsecampground;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;


class UI implements GlobalConstants {

    private MenuItem listProductsMenuItem = new MenuItem("List Products");
    private MenuBar topMenuBar = new MenuBar();
    private ScrollPane scrollPane;
    private Button backButton;


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
        Menu editMenu = new Menu("Edit");
        MenuItem testMenuItem = new MenuItem("Test");

        fileMenu.getItems().add(listProductsMenuItem);
        fileMenu.getItems().add(testMenuItem);

//        MenuItem refundMenuItem = new MenuItem("Refund");
//        MenuItem orderDetailsMenuItem = new MenuItem("Order Details");

        refundMenuItem.setDisable(true);
        orderDetailsMenuItem.setDisable(true);

        editMenu.getItems().addAll(orderDetailsMenuItem, refundMenuItem);

        topMenuBar.getMenus().addAll(fileMenu, editMenu);
        topMenuBar.setPrefWidth(scene.getWidth());

        scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(stackPane.getWidth() -15);
        scrollPane.maxWidthProperty().bind(stage.widthProperty());
        scrollPane.maxHeightProperty().bind(stage.heightProperty());


        mainGridPane.add(topMenuBar, 0, 0);
        mainGridPane.setVgap(6);
        mainGridPane.setHgap(6);
        productsVBox.setSpacing(6);
        ordersVBox.setSpacing(6);

        backButton = new Button("Back");




        stackPane.getChildren().add(mainGridPane);

        // Height Listener
        stackPane.heightProperty().addListener(observable -> {
            mainGridPane.setPrefHeight(stackPane.getHeight());
            scrollPane.maxHeightProperty().bind(stage.heightProperty());
            scrollPane.maxWidthProperty().bind(stage.widthProperty());
        });

        // Width Listener
        stackPane.widthProperty().addListener(observable -> {
            mainGridPane.setPrefWidth(stackPane.getWidth());
            scrollPane.maxWidthProperty().bind(stage.widthProperty());
            scrollPane.maxHeightProperty().bind(stage.heightProperty());
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

        backButton.setOnAction(event -> {

            System.out.println("ScrollPane: " + scrollPane.contentProperty().getValue());

            if (scrollPane.contentProperty().getValue() != null) {
                if (scrollPane.contentProperty().getValue().equals(ordersVBox)) {
                    scrollPane.setContent(productsVBox);
                } else if (scrollPane.contentProperty().getValue().equals(productsVBox)) {
                    scrollPane.setContent(null);
                } else {
                    ResetInterface();
                    // Do nothing
                }
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

        HBox hBox = new HBox(submitButton, backButton);

        mainGridPane.add(hBox, 0, 2);

        buildRows.AddProductsToHBox();
        scrollPane.setContent(null);
        scrollPane.setContent(productsVBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainGridPane.add(scrollPane, 0, 1);


    }

    private void ViewOrders() {
        mainGridPane.getChildren().clear();
        mainGridPane.add(topMenuBar, 0, 0);
        mainGridPane.getChildren().remove(productsVBox);

        scrollPane.setContent(null);
        scrollPane.setContent(ordersVBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainGridPane.add(scrollPane, 0, 1);
        mainGridPane.add(backButton,0,2);

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
