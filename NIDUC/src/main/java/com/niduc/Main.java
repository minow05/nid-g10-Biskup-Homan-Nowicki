package com.niduc;

import com.niduc.votingalgorithms.ConsensusVoting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("NiDUC projekt");
        stage.setScene(scene);
        stage.show();



        // This part of start method is used for testing purposes
        ConsensusVoting dupa = new ConsensusVoting();
        dupa.setParameterValues(Map.of("allowedDifference", 37.21f));
        System.out.println(ConsensusVoting.displayName);
        System.out.println(dupa.getParameterValues().get("allowedDifference"));
    }

    public static void main(String[] args) {
        launch();
    }
}