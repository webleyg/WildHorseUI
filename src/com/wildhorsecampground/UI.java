package com.wildhorsecampground;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class UI implements GlobalConstants{

    private MenuItem listProductsMenuItem = new MenuItem("List Products");


    void buildUI() {

        BuildRows buildRows = new BuildRows();

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setWidth(1500);
        stage.setHeight(600);
        stage.setTitle("Wildhorse Campground");

        MenuBar topMenubar = new MenuBar();
        topMenubar.setId("Top Menu Bar");
        Menu fileMenu = new Menu("File");
        MenuItem testMenuItem = new MenuItem("Test");

        fileMenu.getItems().add(listProductsMenuItem);
        fileMenu.getItems().add(testMenuItem);

        topMenubar.getMenus().add(fileMenu);
        topMenubar.setPrefWidth(scene.getWidth());

        mainGridPane.add(topMenubar, 0,0);
        mainGridPane.setVgap(6);
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
            topMenubar.setPrefWidth(stage.getWidth());
        });


        stage.show();

        /////////BUTTON CODE//////////

        listProductsMenuItem.setOnAction(event -> ViewProducts());
//       ViewProducts();

        testMenuItem.setOnAction(event -> {
            HttpRequests httpRequests = new HttpRequests();
            try {
//                System.out.println(httpRequests.OrdersList("1017"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    void ViewProducts() {

        BuildRows buildRows = new BuildRows();

        if ( !mainGridPane.getChildren().contains(productsVBox) ) {
            System.out.println("Not Empty");
            Button submitButton = new Button("View Orders");

            submitButton.setOnAction(event -> {
                SubmitProducts();
            });

            mainGridPane.add(submitButton,0,2);

            buildRows.AddProductsToHBox();
            mainGridPane.add(productsVBox,0,1);
        }

    }

    void ViewOrders() {
        BuildRows buildRows = new BuildRows();
        mainGridPane.getChildren().clear();



    }

    void SubmitProducts() {

        BuildRows buildRows = new BuildRows();
        HttpRequests httpRequests = new HttpRequests();

        productsVBox.getChildren().forEach(hbox -> {
            ((HBox) hbox).getChildren().forEach(node -> {
//                if (node instanceof Label) {
//
//                    System.out.println("Label: " + node);
//                }
                if (node instanceof CheckBox) {
                    if ((((CheckBox) node).isSelected())) {
                        System.out.println(node.getParent());

                        Parent parent = node.getParent();
                        ((HBox) parent).getChildren().forEach(node1 -> {
                            if (node1 instanceof Label) {
                                if (((Label) node1).getText() != null) {
                                    System.out.println("Label Name: " + ((Label) node1).getText());
                                    buildRows.GetOrders(httpRequests.GetOrderID(((Label) node1).getText()));
                                }
                            }
                        });

//                        System.out.println("Checkbox Selected");
//                        System.out.println("Checkbox: " + node);
                    }


//                        ((HBox) node.getParent()).getChildren().forEach(label -> {
//                            if (label instanceof Label) {
//                                buildRows.GetOrders(httpRequests.GetOrderID(((Label) label).getText()));
//                            }
//                        });

//                    }
                }
            });
            System.out.println("HBOX ID: " + hbox);
        });

    }


}
