package com.niduc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class MainViewController {
    @FXML public Button runButton;
    @FXML public Button pauseButton;
    @FXML public Slider simulationSpeedSlider;

    @FXML public Button sensorAddButton;

    @FXML public Button algorithmChangeButton;

    @FXML public Button signalChangeButton;

    private boolean isRunning = false;
    private boolean isPaused = false;

    @FXML
    public void initialize() {
        pauseButton.setDisable(true);
        simulationSpeedSlider.setValue(SimulationController.getSimulationFramerate());
        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {SimulationController.setSimulationFramerate(newValue.intValue());});
    }

    public void runSimulation(ActionEvent actionEvent) {
        if (!isRunning) {
            this.isRunning = true;
            this.runButton.setText("Reset");
            this.isPaused = false;
            this.pauseButton.setDisable(false);
            this.pauseButton.setText("Pause");
            SimulationController.setup();
            SimulationController.run();
            return;
        }
        this.isRunning = false;
        this.runButton.setText("Run");
        this.isPaused = false;
        this.pauseButton.setDisable(true);
        this.pauseButton.setText("Pause");
        SimulationController.stop();
    }

    public void pauseSimulation(ActionEvent actionEvent) {
        if (!isPaused) {
            this.isPaused = true;
            this.pauseButton.setText("Unpause");
            SimulationController.stop();
            return;
        }
        this.isPaused = false;
        this.pauseButton.setText("Pause");
        SimulationController.run();
    }

    public void addSensor(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void changeAlgorithm(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void changeSignal(ActionEvent actionEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}