package com.niduc;

import com.niduc.errormodels.BiasError;
import com.niduc.errormodels.DriftError;
import com.niduc.errormodels.IntermittentDropoutError;
import com.niduc.errormodels.RandomNoiseError;
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
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view-prim.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("NiDUC projekt");
        stage.setScene(scene);
        stage.show();
        SimulationController.setInputSignal(new InputSignal());

//        SensorTest st1 = new SensorTest();
//        st1.setErrorModel(new BiasError());
//        SimulationController.addSensor(st1);
//        SensorTest st2 = new SensorTest();
//        st2.setErrorModel(new DriftError());
//        SimulationController.addSensor(st2);
//        SensorTest st3 = new SensorTest();
//        st3.setErrorModel(new IntermittentDropoutError());
//        SimulationController.addSensor(st3);
//        SensorTest st4 = new SensorTest();
//        st4.setErrorModel(new RandomNoiseError());
//        SimulationController.addSensor(st4);

//        ArrayList<Sensor> sensors = new ArrayList<>();
//        sensors.add(new SensorTest());
//        ((SensorTest)sensors.getLast()).test__setHeight(0.18230f);
//        sensors.add(new SensorTest());
//        ((SensorTest)sensors.getLast()).test__setHeight(0.18130f);
//        sensors.add(new SensorTest());
//        ((SensorTest)sensors.getLast()).test__setHeight(0.18180f);
//        sensors.add(new SensorTest());
//        ((SensorTest)sensors.getLast()).test__setHeight(0.18235f);
//        sensors.add(new SensorTest());
//        ((SensorTest)sensors.getLast()).test__setHeight(0.18155f);
//
//        ConsensusVoting cv = new ConsensusVoting();
//        WeightedAveragingAlgorithm waa = new WeightedAveragingAlgorithm();
//        GeneralizedMedianVoting gmv = new GeneralizedMedianVoting();
//        FormalizedMajorityVoting fmv = new FormalizedMajorityVoting();
//        waa.setParameterValue("scalingConstant", 1.0f);
//        cv.setParameterValue("allowedDifference", 0.0005f);
//        fmv.setParameterValue("closenessThreshold", 1.0f);
//        System.out.println(cv.vote(sensors));
//        System.out.println(waa.vote(sensors));
//        System.out.println(gmv.vote(sensors));
//        System.out.println(fmv.vote(sensors));
    }

    public static void main(String[] args) {
        launch();
    }
}