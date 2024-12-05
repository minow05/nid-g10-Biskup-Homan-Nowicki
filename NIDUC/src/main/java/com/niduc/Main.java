package com.niduc;

import com.niduc.sensors.Sensor;
import com.niduc.sensors.SensorTest;
import com.niduc.votingalgorithms.ConsensusVoting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view-prim.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("NiDUC projekt");
        stage.setScene(scene);
        stage.show();



        // This part of start method is used for testing purposes
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(new SensorTest());
        ((SensorTest)sensors.getLast()).test__setHeight(0.18230f);
        sensors.add(new SensorTest());
        ((SensorTest)sensors.getLast()).test__setHeight(0.18130f);
        sensors.add(new SensorTest());
        ((SensorTest)sensors.getLast()).test__setHeight(0.18180f);
        sensors.add(new SensorTest());
        ((SensorTest)sensors.getLast()).test__setHeight(0.18235f);
        sensors.add(new SensorTest());
        ((SensorTest)sensors.getLast()).test__setHeight(0.18155f);
        ConsensusVoting cv = new ConsensusVoting();
        cv.setParameterValue("allowedDifference", 0.0005f);
        System.out.println(cv.vote(sensors));

    }

    public static void main(String[] args) {
        launch();
    }
}