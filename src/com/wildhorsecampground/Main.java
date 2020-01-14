package com.wildhorsecampground;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
//        Thread uiThread = new Thread();
        UI ui = new UI();
        ui.buildUI();
//        uiThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
