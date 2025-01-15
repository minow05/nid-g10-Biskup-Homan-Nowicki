package com.niduc;

import com.niduc.errormodels.ErrorModel;
import com.niduc.sensors.Sensor;
import com.niduc.sensors.SensorTest;
import com.niduc.votingalgorithms.VotingAlgorithm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
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
    @FXML public Label votedValueLabel;
    @FXML public Label inputLabel;
    @FXML public GridPane sensorsGridPane;
    @FXML public ComboBox<Sensor> sensorTypeComboBox;
    @FXML public Text sensorTypeDescription;
    @FXML public GridPane sensorParametersGridPane;
    @FXML public Label editedSensorNameLabel;
    @FXML public Label editedSensorTypeLabel;
    @FXML public Text editedSensorDescription;
    @FXML public VBox sensorEditVBox;
    @FXML public Button closeSensorEditButton;
    @FXML public VBox sensorEditAppliedErrorModelsVBox;
    @FXML public ComboBox<ErrorModel> errorModelComboBox;
    @FXML public Button errorAddButton;
    @FXML public Text errorModelDescription;
    @FXML public Label simulationCurrentSpeed;
    @FXML public LineChart<Integer, Float> votedChart;
    @FXML public LineChart<Integer, Float> sensorsChart;
    private XYChart.Series<Integer, Float> heightSeries1;
    private XYChart.Series<Integer, Float> heightSeries2;
    private XYChart.Series<Integer, Float> votedHeightSeries;
    private ArrayList<XYChart.Series<Integer, Float>> sensorsSeries = new ArrayList<>();
    private ArrayList<Parameter> votingAlgorithmParameters;
    private ArrayList<Parameter> sensorParameters;

    private ArrayList<Label> sensorReadingLabels;

    private Integer currentlyEditedSensorIndex = null;


    private boolean isRunning = false;
    private boolean isPaused = false;

    public void update() {
        this.inputLabel.setText(((Float)SimulationController.getInputSignal().getHeight()).toString());
        this.updateSensorReadings();
        this.votedValueLabel.setText(SimulationController.getVotedValue().toString());
        this.updateGraphs();
    }

    @FXML
    public void initialize() {
        SimulationController.setMainViewController(this);
        simulationSpeedSlider.setValue(SimulationController.getSimulationFramerate());
        simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            SimulationController.setSimulationFramerate(newValue.intValue());
            simulationCurrentSpeed.setText(String.valueOf(newValue.intValue()));
        });
        this.initializeVotingAlgorithmComboBox();
        this.initializeSensorTypeComboBox();
        this.initializeErrorModelComboBox();
        this.updateSensors();
        this.closeSensorEdit(null);
    }

    private void initializeVotingAlgorithmComboBox() {
        this.votingAlgorithmComboBox.getItems().addAll(SimulationController.votingAlgorithms);
        this.votingAlgorithmComboBox.setOnAction(event -> {
            VotingAlgorithm selectedVotingAlgorithm = this.votingAlgorithmComboBox.getValue();
            SimulationController.setCurrentVotingAlgorithm(selectedVotingAlgorithm);
            this.votingAlgorithmParameters = selectedVotingAlgorithm.getParameters();
            Map<String, Object> parameterValues = selectedVotingAlgorithm.getParameterValues();
            votingAlgorithmParametersGridPane.getChildren().clear();
            for (int i = 0; i < this.votingAlgorithmParameters.size(); i++) {
                Parameter parameter = this.votingAlgorithmParameters.get(i);
                String parameterID = parameter.getID();
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

    private void initializeSensorTypeComboBox() {
        this.sensorTypeComboBox.getItems().addAll(SimulationController.sensorTypes);
        this.sensorTypeComboBox.setOnAction(event -> {
            Sensor selectedSensor = this.sensorTypeComboBox.getValue();
            this.sensorTypeDescription.setText(selectedSensor == null ? "" : selectedSensor.getDescription());
        });
    }

    private void initializeErrorModelComboBox() {
        errorModelComboBox.setOnAction(event -> {
            ErrorModel selectedError = this.errorModelComboBox.getValue();
            this.errorModelDescription.setText(selectedError == null ? "" : selectedError.getDescription());
        });
    }

    public void runSimulation(ActionEvent actionEvent) {
        if (!isRunning) {
            if (SimulationController.getCurrentVotingAlgorithm() == null)
                return;
            if (SimulationController.getSensors().isEmpty())
                return;
            this.isRunning = true;
            this.runButton.setText("Reset");
            this.isPaused = false;
            this.pauseButton.setDisable(false);
            this.pauseButton.setText("Pause");
            this.sensorsGridPane.setDisable(true);
            this.sensorAddButton.setDisable(true);
            this.votingAlgorithmComboBox.setDisable(true);
            this.votingAlgorithmParametersGridPane.setDisable(true);
            this.closeSensorEdit(null);
            this.initializeGraphs();
            SimulationController.setup();
            SimulationController.run();
            return;
        }
        this.isRunning = false;
        this.runButton.setText("Run");
        this.isPaused = false;
        this.pauseButton.setDisable(true);
        this.pauseButton.setText("Pause");
        this.sensorsGridPane.setDisable(false);
        this.sensorAddButton.setDisable(false);
        this.votingAlgorithmComboBox.setDisable(false);
        this.votingAlgorithmParametersGridPane.setDisable(false);
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
        Sensor selectedSensorType = this.sensorTypeComboBox.getValue();
        if (selectedSensorType == null) return;
        SimulationController.addSensor(selectedSensorType.getNewInstance());
        updateSensors();
    }

    private void editSensor(int index) {
        Sensor selectedSensor = SimulationController.getSensors().get(index);
        this.editedSensorNameLabel.setText(String.format("Sensor %d", index + 1));
        this.editedSensorTypeLabel.setText(selectedSensor.getDisplayName());
        this.editedSensorDescription.setText(selectedSensor.getDescription());
        this.sensorParameters = selectedSensor.getParameters();
        Map<String, Object> parameterValues = selectedSensor.getParameterValues();
        this.sensorParametersGridPane.getChildren().clear();
        for (int i = 0; i < this.sensorParameters.size(); i++) {
            Parameter parameter = this.sensorParameters.get(i);
            String parameterID = parameter.getID();
            this.sensorParametersGridPane.add(new Label(parameter.getDisplayName()), 0, i);
            TextField parameterTextField = new TextField(parameterValues.get(parameterID).toString());
            parameterTextField.setOnAction(textFieldEvent -> {
                Object value = parameter.validateValue(parameterTextField.getText());
                if (value != null)
                    selectedSensor.setParameterValue(parameterID, value);
                parameterTextField.setText(selectedSensor.getParameterValues().get(parameterID).toString());
            });
            this.sensorParametersGridPane.add(parameterTextField, 1, i);
            this.sensorParametersGridPane.add(new Label(parameter.getType().getSimpleName()), 2, i);
        }
        this.errorAddButton.setOnAction(event -> {
            ErrorModel selectedError = this.errorModelComboBox.getValue();
            if (selectedError == null) return;
            selectedSensor.addError(selectedError.getNewInstance());
            updateErrorModelsVBox(selectedSensor);
        });

        updateErrorModelsVBox(selectedSensor);

        sensorEditVBox.setVisible(true);
        sensorEditVBox.setManaged(true);
    }

    private void updateErrorModelsVBox(Sensor selectedSensor) {
        this.sensorEditAppliedErrorModelsVBox.getChildren().clear();
        ArrayList<ErrorModel> selectedSensorAppliedErrors = selectedSensor.getAppliedErrors();
        errorModelComboBox.getItems().setAll(selectedSensor.getAllowedErrors());
        for (int i = 0; i < selectedSensorAppliedErrors.size(); i++) {
            final int final_i = i;
            ErrorModel selectedSensorAppliedError = selectedSensorAppliedErrors.get(i);
            GridPane errorGridPane = new GridPane();
            errorGridPane.styleProperty().set("-fx-padding: 10;");
            errorGridPane.setHgap(10);
            errorGridPane.setVgap(10);
            errorGridPane.add(new Label("Error type: "), 0, 0);
            errorGridPane.add(new Label(selectedSensorAppliedError.getDisplayName()), 1, 0);
            Label errorDescriptionLabel = new Label(selectedSensorAppliedError.getDescription());
            GridPane.setColumnSpan(errorDescriptionLabel, 3);
            errorGridPane.add(errorDescriptionLabel, 0, 1);
            ArrayList<Parameter> errorParameters = selectedSensorAppliedError.getParameters();
            Map<String, Object> errorParameterValues = selectedSensorAppliedError.getParameterValues();
            for (int j = 0; j < errorParameters.size(); j++) {
                Parameter parameter = errorParameters.get(j);
                String parameterID = parameter.getID();
                errorGridPane.add(new Label(parameter.getDisplayName()), 0, j + 2);
                TextField parameterTextField = new TextField(errorParameterValues.get(parameterID).toString());
                parameterTextField.setOnAction(textFieldEvent -> {
                    Object value = parameter.validateValue(parameterTextField.getText());
                    if (value != null)
                        selectedSensorAppliedError.setParameterValue(parameterID, value);
                    parameterTextField.setText(selectedSensorAppliedError.getParameterValues().get(parameterID).toString());
                });
                errorGridPane.add(parameterTextField, 1, j + 2);
                errorGridPane.add(new Label(parameter.getType().getSimpleName()), 2, j + 2);
            }
            int buttonsRow = 2 + errorParameters.size();
            Button moveErrorUpButton = new Button("Move up");
            Button moveErrorDownButton = new Button("Move down");
            Button deleteErrorButton = new Button("Delete");

            moveErrorUpButton.setOnAction(event -> {
                selectedSensor.moveErrorUp(final_i);
                updateErrorModelsVBox(selectedSensor);
            });
            moveErrorDownButton.setOnAction(event -> {
                selectedSensor.moveErrorDown(final_i);
                updateErrorModelsVBox(selectedSensor);
            });
            deleteErrorButton.setOnAction(event -> {
                selectedSensor.removeError(final_i);
                updateErrorModelsVBox(selectedSensor);
            });

            deleteErrorButton.styleProperty().set("-fx-text-fill: #770000");
            errorGridPane.add(moveErrorUpButton, 0, buttonsRow);
            errorGridPane.add(moveErrorDownButton, 1, buttonsRow);
            errorGridPane.add(deleteErrorButton, 2, buttonsRow);
            this.sensorEditAppliedErrorModelsVBox.getChildren().add(errorGridPane);
        }
    }

    private void deleteSensor(int index) {
        // TODO: Remove this quickfix xD
        // NOTE: I don't think I will
        if (sensorEditVBox.isManaged()) return;
        SimulationController.removeSensor(index);
        updateSensors();
    }

    public void updateSensors() {
        this.sensorsGridPane.getChildren().clear();
        this.sensorReadingLabels = new ArrayList<>();
        ArrayList<Sensor> sensorsList = SimulationController.getSensors();
        for (int i = 0; i < sensorsList.size(); i++) {
            final int final_i = i;
            Sensor sensor = sensorsList.get(i);
            Label nameLabel = new Label(String.format("Sensor %d", i + 1));
            nameLabel.styleProperty().set("-fx-font-weight: bold");
            Label sensorTypeLabel = new Label(sensor.getDisplayName());
            Label sensorReadingLabel = new Label(sensor.getDisplayHeight());
            sensorReadingLabel.styleProperty().set("-fx-font-weight: bold");
            this.sensorReadingLabels.add(sensorReadingLabel);
            Button sensorEditButton = new Button("Edit");
            sensorEditButton.setOnAction(event -> editSensor(final_i));
            Button sensorDeleteButton = new Button("Delete");
            sensorDeleteButton.setOnAction(event -> deleteSensor(final_i));
            sensorDeleteButton.styleProperty().set("-fx-text-fill: #770000");

            this.sensorsGridPane.add(nameLabel, 0, i);
            this.sensorsGridPane.add(sensorTypeLabel, 1, i);
            this.sensorsGridPane.add(sensorReadingLabel, 2, i);
            this.sensorsGridPane.add(sensorEditButton, 3, i);
            this.sensorsGridPane.add(sensorDeleteButton, 4, i);
        }
    }

    private void updateSensorReadings() {
        ArrayList<Sensor> sensorsList = SimulationController.getSensors();
        for (int i = 0; i < sensorsList.size(); i++) {
            Sensor sensor = sensorsList.get(i);
            Label sensorReadingLabel = this.sensorReadingLabels.get(i);
            sensorReadingLabel.setText(sensor.getDisplayHeight());
        }
    }

    public void closeSensorEdit(ActionEvent actionEvent) {
        sensorEditVBox.setVisible(false);
        sensorEditVBox.setManaged(false);
        this.currentlyEditedSensorIndex = null;
    }

    public void initializeGraphs() {
        this.votedChart.getData().clear();
        this.sensorsChart.getData().clear();
        this.heightSeries1 = new XYChart.Series<>();
        this.heightSeries1.setName("Height");
        this.heightSeries2 = new XYChart.Series<>();
        this.heightSeries2.setName("Height");
        this.votedHeightSeries = new XYChart.Series<>();
        this.votedHeightSeries.setName("Voted Height");
        this.votedChart.getData().add(heightSeries1);
        this.votedChart.getData().add(votedHeightSeries);
        this.sensorsChart.getData().add(heightSeries2);

        this.sensorsSeries.clear();
        for (int i = 0; i < SimulationController.getSensors().size(); i++) {
            XYChart.Series<Integer, Float> sensorSeries = new XYChart.Series<>();
            sensorSeries.setName("Sensor " + (i + 1));
            this.sensorsSeries.add(sensorSeries);
            this.sensorsChart.getData().add(sensorSeries);
        }
//        updateGraphs();
    }

    public void updateGraphs() {
        ArrayList<Sensor> sensorsList = SimulationController.getSensors();
        Integer currentTime = SimulationController.getTime();
        this.heightSeries1.getData().add(new XYChart.Data<>(currentTime, SimulationController.getInputSignal().getHeight()));
        this.heightSeries2.getData().add(new XYChart.Data<>(currentTime, SimulationController.getInputSignal().getHeight()));
        this.votedHeightSeries.getData().add(new XYChart.Data<>(currentTime, SimulationController.getVotedValue()));
        for (int i = 0; i < sensorsList.size(); i++)
            this.sensorsSeries.get(i).getData().add(new XYChart.Data<>(currentTime, sensorsList.get(i).getHeight()));
    }
}