package com.niduc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view-prim.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("NiDUC projekt");
        stage.setScene(scene);
        stage.show();
        SimulationController.setInputSignal(new InputSignal());
    }

    public static void main(String[] args) {
        launch();
    }
}