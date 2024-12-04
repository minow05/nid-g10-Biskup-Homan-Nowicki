package com.niduc;

import com.niduc.sensors.Sensor;
import com.niduc.sensors.SensorTest;
import com.niduc.votingalgorithms.ConsensusVoting;
import com.niduc.votingalgorithms.FormalizedMajorityVoting;
import com.niduc.votingalgorithms.GeneralizedMedianVoting;
import com.niduc.votingalgorithms.WeightedAveragingAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
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
//        sensors.add(new SensorTest());
//        ((SensorTest)sensors.getLast()).test__setHeight(0.12125f);
        ConsensusVoting cv = new ConsensusVoting();
        WeightedAveragingAlgorithm waa = new WeightedAveragingAlgorithm();
        GeneralizedMedianVoting gmv = new GeneralizedMedianVoting();
        FormalizedMajorityVoting fmv = new FormalizedMajorityVoting();
        waa.setParameterValue("scalingConstant", 1.0f);
        cv.setParameterValue("allowedDifference", 0.0005f);
        fmv.setParameterValue("closenessThreshold", 1.0f);
        System.out.println(cv.vote(sensors));
        System.out.println(waa.vote(sensors));
        System.out.println(gmv.vote(sensors));
        System.out.println(fmv.vote(sensors));
    }

    public static void main(String[] args) {
        launch();
    }
}