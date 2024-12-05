package com.niduc;

import com.niduc.votingalgorithms.VotingAlgorithm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Map;

public class MainViewController {
    @FXML public Button runButton;
    @FXML public Button pauseButton;
    @FXML public Slider simulationSpeedSlider;

    @FXML public Button sensorAddButton;

    @FXML public Button algorithmChangeButton;

    @FXML public Button signalChangeButton;
    @FXML public ComboBox<VotingAlgorithm> votingAlgorithmComboBox;
    @FXML public GridPane votingAlgorithmParametersGridPane;
    @FXML public Text votingAlgorithmDescription;
    private ArrayList<Parameter> votingAlgorithmParameters;


    private boolean isRunning = false;
    private boolean isPaused = false;

    @FXML
    public void initialize() {
        simulationSpeedSlider.setValue(SimulationController.getSimulationFramerate());
        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {SimulationController.setSimulationFramerate(newValue.intValue());});
        this.initializeVotingAlgorithmComboBox();
    }

    private void initializeVotingAlgorithmComboBox() {
        this.votingAlgorithmComboBox.getItems().addAll(SimulationController.votingAlgorithms);
        this.votingAlgorithmComboBox.setOnAction(event -> {
            VotingAlgorithm selectedVotingAlgorithm = this.votingAlgorithmComboBox.getValue();
            System.out.println("Selected Algorithm: " + selectedVotingAlgorithm.getDisplayName());
            SimulationController.setCurrentVotingAlgorithm(selectedVotingAlgorithm);
            this.votingAlgorithmParameters = selectedVotingAlgorithm.getParameters();
            Map<String, Object> parameterValues = selectedVotingAlgorithm.getParameterValues();
            votingAlgorithmParametersGridPane.getChildren().clear();
            for (int i = 0; i < this.votingAlgorithmParameters.size(); i++) {
                Parameter parameter = this.votingAlgorithmParameters.get(i);
                String parameterID = parameter.getID();
                System.out.println("Parameter ID: " + parameterID);
                this.votingAlgorithmParametersGridPane.add(new Label(parameter.getDisplayName()), 0, i);
                TextField parameterTextField = new TextField(parameterValues.get(parameterID).toString());
                parameterTextField.setOnAction(textFieldEvent -> {
                    Object value = parameter.validateValue(parameterTextField.getText());
                    if (value != null)
                        SimulationController.getCurrentVotingAlgorithm().setParameterValue(parameterID, value);
                    parameterTextField.setText(selectedVotingAlgorithm.getParameterValues().get(parameterID).toString());
                });
                this.votingAlgorithmParametersGridPane.add(parameterTextField, 1, i);
                this.votingAlgorithmParametersGridPane.add(new Label(parameter.getType().getSimpleName()), 2, i);
                this.votingAlgorithmDescription.setText(selectedVotingAlgorithm.getDescription());
            }
        });
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